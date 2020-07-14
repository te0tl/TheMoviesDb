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
import com.te0tl.themoviesdb.domain.entity.Video
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * 10min cache memory
 */
private const val cacheTime = 60_0000L

class MoviesRepositoryImpl(private val context: Context, private val tmdbApi: TmdbApi) : MoviesRepository {

    private val memoryMoviesCache by lazy {
        mutableMapOf<Category, Triple<MutableList<Movie>, Long, Int>>()
    }

    private val memoryMoviesDetailsCache by lazy {
        mutableMapOf<Int, Pair<MovieDetail, Long>>()
    }

    private val memoryMoviesDetailsVideosCache by lazy {
        mutableMapOf<Int, Pair<List<Video>, Long>>()
    }

    override suspend fun getMovies(category: Category, page: Int): Result<List<Movie>, String> =
        withContext(Dispatchers.IO) {
            try {
                when {
                    !context.hasNetworkConnection() -> {
                        Result.Failure(context.getString(R.string.no_network_error))
                    }

                    !isCacheMoviesExpired(category, page) -> {
                        Result.Success(memoryMoviesCache[category]!!.first)
                    }

                    else -> {
                        Result.Success(tmdbApi.getMovies(Mapper.toCategoryDto(category), page).movies
                            .map {
                                Movie(
                                    it.id, it.title, it.overview, it.releaseDate,
                                    BASE_URL_IMAGES + "w400/" + it.pathUrl
                                )
                            })
                    }
                }

            } catch (e: Exception) {
                Result.Failure(e.safeMessage)
            }

        }

    override suspend fun getMovie(category: Category, id: Int): Result<Movie, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(id: Int, category: Category): Result<MovieDetail, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieVideos(id: Int, category: Category): Result<List<Video>, String> {
        TODO("Not yet implemented")
    }


    private fun isCacheMoviesExpired(category: Category, page : Int) : Boolean {
        return if (memoryMoviesCache[category] != null
            && memoryMoviesCache[category]?.third == page) {
            val currentTimestamp = System.currentTimeMillis()
            val moviesTimestamp = memoryMoviesCache[category]!!.second
            currentTimestamp - moviesTimestamp > cacheTime
        } else {
            true
        }
    }

    private fun isCacheMovieExpired(id: Int) : Boolean {
        return if (memoryMoviesDetailsCache[id] != null) {
            val currentTimestamp = System.currentTimeMillis()
            val moviesTimestamp = memoryMoviesDetailsCache[id]!!.second
            currentTimestamp - moviesTimestamp > cacheTime
        } else {
            true
        }
    }

    private fun isCacheMovieVideosExpired(id: Int) : Boolean {
        return if (memoryMoviesDetailsVideosCache[id] != null) {
            val currentTimestamp = System.currentTimeMillis()
            val moviesTimestamp = memoryMoviesDetailsVideosCache[id]!!.second
            currentTimestamp - moviesTimestamp > cacheTime
        } else {
            true
        }
    }

}