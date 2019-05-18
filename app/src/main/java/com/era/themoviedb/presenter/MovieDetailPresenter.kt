package com.era.themoviedb.presenter

import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.contract.MvpMovieDetail
import com.era.themoviedb.view.entity.Movie
import com.era.themoviedb.view.entity.MovieDetail
import io.reactivex.rxkotlin.subscribeBy


class MovieDetailPresenter(private val model : MvpMovieDetail.Model) : MvpMovieDetail.Presenter {

    private var view : MvpMovieDetail.View ? = null

    override fun onViewAvailable(view:  BaseView?) {
        this.view = view as MvpMovieDetail.View

    }

    override fun onMovieDataReceived(id: Int?, category: String?) {
        if (id == null || category == null) {
            view?.showErrorMessage("error")
            view?.destroyView()
        } else {
            getMovie(id, category)
        }
    }

    private fun getMovie(id : Int, category: String) {
        view?.showProgress()
        disposables.add(model.getMovie(id, category)
            .subscribeBy(
                onError =  {
                    view?.showErrorMessage("error")
                    view?.stopShowingProgress()
                },
                onSuccess = { movie : Movie ->
                    view?.showMovie(movie)
                    view?.stopShowingProgress()
                    getMovieDetail(id, category)
                }
            ))
    }

    private fun getMovieDetail(id : Int, category: String) {
        view?.showProgress()
        disposables.add(model.getMovieDetail(id, category)
            .subscribeBy(
                onError =  {
                    view?.showErrorMessage("error")
                    view?.stopShowingProgress()
                },
                onSuccess = { movie : MovieDetail ->
                    view?.showMovieDetails(movie)
                    view?.stopShowingProgress()
                }
            ))
    }

}