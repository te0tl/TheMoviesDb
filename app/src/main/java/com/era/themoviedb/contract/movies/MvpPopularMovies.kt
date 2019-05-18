package com.era.themoviedb.contract.movies

import com.era.themoviedb.contract.common.BaseModel
import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.view.entity.Movie
import io.reactivex.Observable

interface MvpPopularMovies {

    interface Model : BaseModel {

        fun getMovies(page: Int): Observable<Movie>
    }

    interface View : BaseView {
        fun onNewMovie(movie : Movie)

        fun performSearch(query : String)
    }

    interface Presenter : BasePresenter {
        fun onQuerySubmit(query : String)

        fun onRequestedMoreMovies()
    }
}