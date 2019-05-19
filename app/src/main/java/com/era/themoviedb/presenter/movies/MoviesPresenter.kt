package com.era.themoviedb.presenter.movies

import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.contract.movies.MvpMovies
import com.era.themoviedb.contract.movies.MvpPopularMovies
import com.era.themoviedb.contract.movies.MvpTopRatedMovies
import com.era.themoviedb.contract.movies.MvpUpcomingMovies
import com.era.themoviedb.framework.rx.AndroidDisposable
import com.era.themoviedb.view.entity.Category
import com.era.themoviedb.view.entity.Movie


class MoviesPresenter(private val model : MvpMovies.Model, override val disposables : AndroidDisposable,
                      private val popularMoviesPresenterMovies : MvpPopularMovies.Presenter,
                      private val topRatedMoviesPresenterMovies : MvpTopRatedMovies.Presenter,
                      private val UpcomingMoviesPresenterMovies : MvpUpcomingMovies.Presenter) : MvpMovies.Presenter {

    private var view : MvpMovies.View ? = null
    private var categorySelected : Category = Category.Popular

    override fun onViewAvailable(view:  BaseView?) {
        this.view = view as MvpMovies.View
        this.view?.showPopularMovies()
    }

    override fun onShowPopularMoviesSelected() {
        categorySelected = Category.Popular
        this.view?.showPopularMovies()
    }

    override fun onShowTopRatedMoviesSelected() {
        categorySelected = Category.TopRated
        this.view?.showTopRatedMovies()
    }

    override fun onShowUpcomingMoviesSelected() {
        categorySelected = Category.Upcoming
        this.view?.showUpcomingMovies()
    }

    override fun onClickMovie(movie: Movie) {
        this.view?.gotoDetailMovie(movie)
    }

    override fun onQuerySubmit(query: String) {
        when(categorySelected) {
            Category.Popular -> { popularMoviesPresenterMovies.onQuerySubmit(query) }
            Category.TopRated -> { topRatedMoviesPresenterMovies.onQuerySubmit(query) }
            Category.Upcoming -> { UpcomingMoviesPresenterMovies.onQuerySubmit(query) }
        }
    }

}