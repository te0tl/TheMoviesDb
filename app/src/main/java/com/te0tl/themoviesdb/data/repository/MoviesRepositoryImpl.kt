package com.te0tl.themoviesdb.data.repository

import android.content.Context
import com.te0tl.commons.domain.Result
import com.te0tl.commons.platform.extension.android.hasNetworkConnection
import com.te0tl.commons.platform.extension.safeMessage
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.data.api.BASE_URL_IMAGES
import com.te0tl.themoviesdb.data.api.TmdbApi
import com.te0tl.themoviesdb.data.api.dto.Mapper
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.entity.YoutubeVideo
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * 10min cache memory
 */
private const val cacheTime = 60_0000L

class MoviesRepositoryImpl(private val context: Context, private val tmdbApi: TmdbApi) :
    MoviesRepository {

    private val memoryMoviesCache by lazy {
        mutableMapOf<Category, Triple<MutableList<Movie>, Long, Int>>()
    }

    private val memoryMoviesDetailsCache by lazy {
        mutableMapOf<Int, Pair<MovieDetail, Long>>()
    }

    override suspend fun getMovies(category: Category, page: Int): Result<List<Movie>, String> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    !isCacheMoviesExpired(category, page) -> {
                        Result.Success(memoryMoviesCache[category]!!.first)
                    }

                    else -> {
                        if (!context.hasNetworkConnection()) {
                            Result.Failure(context.getString(R.string.no_network_error))
                        } else {
                            Result.Success(tmdbApi.getMovies(
                                Mapper.toCategoryDto(category),
                                page
                            ).movies
                                .map {
                                    Movie(
                                        it.id, it.title, it.overview, it.releaseDate,
                                        BASE_URL_IMAGES + "w400/" + it.pathUrl
                                    )
                                })
                        }

                    }
                }

            } catch (e: Exception) {
                Result.Failure(e.safeMessage)
            }

        }

    override suspend fun getMovieDetail(id: Int): Result<MovieDetail, String> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    !isCacheMovieExpired(id) && memoryMoviesDetailsCache[id]?.first != null -> {
                        Result.Success(memoryMoviesDetailsCache[id]?.first!!)
                    }

                    else -> {

                        if (!context.hasNetworkConnection()) {
                            Result.Failure(context.getString(R.string.no_network_error))
                        } else {
                            val movieRepo = tmdbApi.getMovie(id)
                            val movies =
                                when (val videosYoutube = getMovieYoutubeVideos(id)) {
                                    is Result.Success -> {
                                        videosYoutube.data
                                    }
                                    else -> {
                                        emptyList<YoutubeVideo>()
                                    }
                                }

                            val movieDetail = MovieDetail(
                                movieRepo.id,
                                movieRepo.title,
                                movieRepo.originalTitle,
                                movieRepo.overview,
                                movieRepo.releaseDate,
                                BASE_URL_IMAGES + "w400/" + movieRepo.pathUrl,
                                movieRepo.homePage ?: "",
                                movies
                            )
                            memoryMoviesDetailsCache[id] = movieDetail to System.currentTimeMillis()
                            Result.Success(movieDetail)
                        }

                    }
                }

            } catch (e: Exception) {
                Result.Failure(e.safeMessage)
            }
        }

    suspend fun getMovieYoutubeVideos(
        id: Int
    ): Result<List<YoutubeVideo>, String> = withContext(Dispatchers.IO) {
        try {
            if (!context.hasNetworkConnection()) {
                Result.Failure(context.getString(R.string.no_network_error))
            } else {
                Result.Success(tmdbApi.getVideos(id).videos
                    .filter { it.site.equals("YouTube") }
                    .map {
                        YoutubeVideo(it.key, it.name)
                    })
            }
        } catch (e: Exception) {
            Result.Failure(e.safeMessage)
        }
    }


    private fun isCacheMoviesExpired(category: Category, page: Int): Boolean {
        return if (memoryMoviesCache[category] != null
            && memoryMoviesCache[category]?.third == page
        ) {
            val currentTimestamp = System.currentTimeMillis()
            val moviesTimestamp = memoryMoviesCache[category]!!.second
            currentTimestamp - moviesTimestamp > cacheTime
        } else {
            true
        }
    }

    private fun isCacheMovieExpired(id: Int): Boolean {
        return if (memoryMoviesDetailsCache[id] != null) {
            val currentTimestamp = System.currentTimeMillis()
            val moviesTimestamp = memoryMoviesDetailsCache[id]!!.second
            currentTimestamp - moviesTimestamp > cacheTime
        } else {
            true
        }
    }

}