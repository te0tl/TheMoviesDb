package com.era.themoviedb.presenter.movies

import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.contract.movies.MvpUpcomingMovies
import com.era.themoviedb.framework.rx.AndroidDisposable
import com.era.themoviedb.view.entity.Movie
import io.reactivex.rxkotlin.subscribeBy

class UpcomingMoviesPresenter(private val model : MvpUpcomingMovies.Model) : MvpUpcomingMovies.Presenter {
    override val disposables by lazy{ AndroidDisposable() }
    private var view: MvpUpcomingMovies.View? = null
    private var page = 0

    override fun onViewAvailable(view: BaseView?) {
        this.view = view as MvpUpcomingMovies.View
        page = 1
        loadMovies()
    }

    private fun loadMovies() {
        view?.showProgress()
        disposables.add(model.getMovies(page)
            .subscribeBy(
                onError =  {
                    view?.stopShowingProgress()
                    view?.showErrorMessage("error")
                },
                onNext = { newMovie : Movie ->
                    view?.stopShowingProgress()
                    view?.apply {
                        onNewMovie(newMovie)
                    }
                }
            ))
    }

    override fun onQuerySubmit(query: String) {
        view?.performSearch(query)
    }

    override fun onRequestedMoreMovies() {
        page++
        loadMovies()
    }
}