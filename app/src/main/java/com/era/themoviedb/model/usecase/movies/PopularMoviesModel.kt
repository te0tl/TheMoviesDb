package com.era.themoviedb.model.usecase.movies

import com.era.themoviedb.contract.movies.MvpPopularMovies
import com.era.themoviedb.contract.common.BaseModel
import com.era.themoviedb.model.data.repository.MoviesRepository
import com.era.themoviedb.view.entity.Category
import com.era.themoviedb.view.entity.Movie as MovieView
import com.era.themoviedb.model.data.repository.entity.Movie as MovieModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.inject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar

class PopularMoviesModel : BaseModel, MvpPopularMovies.Model {
    private val moviesRepository by inject<MoviesRepository>()

    override fun getMovies(page: Int): Observable<MovieView> {
        return Observable.create<MovieView> { emitter : ObservableEmitter<MovieView> ->
            moviesRepository.getMovies("popular", page)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribeBy(
                    onNext = { movieModel : MovieModel ->
                        val date = try {
                            val formatter = SimpleDateFormat("yyyy-MM-dd")
                            val cal = Calendar.getInstance()
                            cal.time = formatter.parse(movieModel.releaseDate)
                            cal.get(Calendar.YEAR).toString()
                        } catch (e : Exception) {
                            null
                        }
                        val movie = MovieView(movieModel.id, movieModel.title, movieModel.overview,
                            movieModel.pathUrl, date, Category.Popular)
                        emitter.onNext(movie)
                    },
                    onError = {
                        emitter.onError(it)
                    }
                )
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
