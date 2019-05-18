package com.era.themoviedb.contract

import com.era.themoviedb.contract.common.BaseModel
import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.view.entity.Movie
import com.era.themoviedb.view.entity.MovieDetail
import io.reactivex.Single

interface MvpMovieDetail {

    interface Model : BaseModel {

        fun getMovieDetail(id: Int, category: String): Single<MovieDetail>

        fun getMovie(id: Int, category: String): Single<Movie>
    }

    interface View : BaseView {
        fun showMovie(movie : Movie)
        fun showMovieDetails(movie : MovieDetail)

    }

    interface Presenter : BasePresenter {
        fun onMovieDataReceived(id: Int?, category: String?)
    }
}