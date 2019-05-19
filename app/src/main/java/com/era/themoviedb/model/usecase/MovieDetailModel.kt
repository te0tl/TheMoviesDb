package com.era.themoviedb.model.usecase

import com.era.themoviedb.contract.common.BaseModel
import com.era.themoviedb.model.data.repository.MoviesRepository
import com.era.themoviedb.model.data.repository.entity.Video
import com.era.themoviedb.contract.MvpMovieDetail
import com.era.themoviedb.view.entity.Category
import com.era.themoviedb.view.entity.YoutubeVideo
import com.orhanobut.logger.Logger
import com.era.themoviedb.view.entity.Movie as MovieView
import com.era.themoviedb.view.entity.MovieDetail as MovieDetailView
import com.era.themoviedb.model.data.repository.entity.Movie as MovieModel
import com.era.themoviedb.model.data.repository.entity.MovieDetail as MovieDetailM
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailModel : BaseModel, MvpMovieDetail.Model {
    private val moviesRepository by inject<MoviesRepository>()

    override fun getMovie(id: Int, category: String): Single<MovieView> {
        return Single.create<MovieView> { emitter : SingleEmitter<MovieView> ->
            moviesRepository.getMovie(id, category)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribeBy(
                    onSuccess = { movieModel : MovieModel ->
                        val date = try {
                            val formatter = SimpleDateFormat("yyyy-MM-dd")
                            val cal = Calendar.getInstance()
                            cal.time = formatter.parse(movieModel.releaseDate)
                            cal.get(Calendar.YEAR).toString()
                        } catch (e : Exception) {
                            null
                        }

                        val categoryView = when(category) {
                                "top_rated" -> Category.TopRated
                                "upcoming" -> Category.Upcoming
                            else -> Category.Popular
                        }

                        val movie = MovieView(movieModel.id, movieModel.title, movieModel.overview,
                            movieModel.pathUrl, date, categoryView)
                        emitter.onSuccess(movie)
                    },
                    onError = {
                        it.printStackTrace()
                    }
                )
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getMovieDetail(id: Int, category: String): Single<MovieDetailView> {
        return Single.create<MovieDetailView> { emitter : SingleEmitter<MovieDetailView> ->
            Single.zip(
                moviesRepository.getMovieDetail(id, category)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(Schedulers.computation()),
                moviesRepository.getMovieVideos(id, category)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(Schedulers.computation()), mergeResults())
                .subscribeBy(
                    onSuccess = { movieDetailView : MovieDetailView ->
                        emitter.onSuccess(movieDetailView)
                    },
                    onError = {
                        emitter.onError(it)
                    }
                )
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun mergeResults(): BiFunction<MovieDetailM, List<Video>, MovieDetailView> {
        return BiFunction { movieModel : MovieDetailM,  movieVideos : List<Video> ->
            val date = try {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val cal = Calendar.getInstance()
                cal.time = formatter.parse(movieModel.releaseDate)
                cal.get(Calendar.YEAR).toString()
            } catch (e : Exception) {
                null
            }

            val videos = movieVideos.filter { it.site == "YouTube" }.map { YoutubeVideo(it.key, it.name) }


            MovieDetailView(movieModel.id, movieModel.title, movieModel.overview,
                movieModel.pathUrl, date, movieModel.originalTitle, movieModel.homePage, videos)
        }
    }

}
