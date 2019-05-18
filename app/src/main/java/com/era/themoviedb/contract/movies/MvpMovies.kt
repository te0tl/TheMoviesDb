package com.era.themoviedb.contract.movies

import com.era.themoviedb.contract.common.BaseModel
import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.view.entity.Movie

interface MvpMovies {

    interface Model : BaseModel {

    }

    interface View : BaseView {
        fun showPopularMovies()
        fun showTopRatedMovies()
        fun showUpcomingMovies()

        fun gotoDetailMovie(movie: Movie)
    }

    interface Presenter : BasePresenter {
        fun onShowPopularMoviesSelected()
        fun onShowTopRatedMoviesSelected()
        fun onShowUpcomingMoviesSelected()

        fun onClickMovie(movie : Movie)

        fun onQuerySubmit(query : String)
    }
}