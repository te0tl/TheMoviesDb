package com.era.themoviedb.model.data.repository

import com.era.themoviedb.model.data.repository.entity.*
import com.era.themoviedb.model.data.service.Retrofit
import com.era.themoviedb.model.data.service.TheMoviesDbApi
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.inject


class MoviesRepositoryTheMovieDbOrg : MoviesRepository {
    private val moviesApi by inject<TheMoviesDbApi>()

    /**
     * 10min cache memory
     */
    private val cacheTime = 60_0000L

    private val memoryMoviesCache by lazy {
        mutableMapOf<String, Triple<MutableList<Movie>, Long, Int>>()
    }

    private val memoryMoviesDetailsCache by lazy {
        mutableMapOf<Int, Pair<MovieDetail, Long>>()
    }

    private val memoryMoviesDetailsVideosCache by lazy {
        mutableMapOf<Int, Pair<List<Video>, Long>>()
    }

    override fun getMovies(category: String, page: Int) : Observable<Movie> {
        return if (!isCacheMoviesExpired(category, page)) {
            return Observable.create<Movie> { emitter : ObservableEmitter<Movie> ->
                memoryMoviesCache[category]?.first?.forEach {
                    emitter.onNext(it)
                }
            }
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
        } else {
            downloadMovies(category, page)
        }
    }

    override fun getMovie(id: Int, category: String): Single<Movie> {
        return Single.create<Movie> { emitter : SingleEmitter<Movie> ->
            val results = memoryMoviesCache[category]?.first?.filter { id == it.id }

            emitter.onSuccess(results?.get(0)!!)
        }
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
    }

    override fun getMovieDetail(id: Int, category: String): Single<MovieDetail> {
        return if (!isCacheMovieExpired(id)) {
            return Single.create<MovieDetail> { emitter : SingleEmitter<MovieDetail> ->
                memoryMoviesDetailsCache[id]?.first?.apply {
                    emitter.onSuccess(this)
                }
            }
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
        } else {
            downloadMovieDetail(id)
        }
    }

    override fun getMovieVideos(id: Int, category: String): Single<List<Video>> {
        return if (!isCacheMovieVideosExpired(id)) {
            return Single.create<List<Video>> { emitter : SingleEmitter<List<Video>> ->
                memoryMoviesDetailsVideosCache[id]?.first?.apply {
                    emitter.onSuccess(this)
                }
            }
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
        } else {
            downloadVideos(id)
        }
    }

    private fun isCacheMoviesExpired(category: String, page : Int) : Boolean {
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

    private fun downloadMovies(category: String, page : Int) : Observable<Movie>{
        return Observable.create<Movie> { emitter : ObservableEmitter<Movie> ->
            val movies = mutableListOf<Movie>()
            moviesApi.getMovies(category, page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = { response : ResponseMovies ->
                        response.movies.forEach {
                            it.pathUrl = Retrofit.baseUrlImages + "w400/" + it.pathUrl
                            emitter.onNext(it)
                            movies.add(it)
                        }
                        if (memoryMoviesCache[category] == null) {
                            memoryMoviesCache[category] = Triple(movies, System.currentTimeMillis(), page)
                        } else {
                            memoryMoviesCache[category]?.first?.addAll(movies)
                        }
                    },
                    onError = {
                        emitter.onError(it)
                    }
                )

        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())

    }

    private fun downloadMovieDetail(id : Int) : Single<MovieDetail> {
        return Single.create<MovieDetail> { emitter : SingleEmitter<MovieDetail> ->
            moviesApi.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = { movieDetail : MovieDetail ->
                        movieDetail.pathUrl = Retrofit.baseUrlImages + "w500/" + movieDetail.pathUrl
                        emitter.onSuccess(movieDetail)
                        memoryMoviesDetailsCache[id] = movieDetail to System.currentTimeMillis()
                    },
                    onError = {
                        emitter.onError(it)
                    }
                )

        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
    }

    private fun downloadVideos(id : Int) : Single<List<Video>>{
        return Single.create<List<Video>> { emitter : SingleEmitter<List<Video>> ->
            moviesApi.getVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = {
                        val videos = it.videos
                        emitter.onSuccess(videos)
                        memoryMoviesDetailsVideosCache[id] = videos to System.currentTimeMillis()
                    },
                    onError = {
                        emitter.onError(it)
                    }
                )

        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
    }
}