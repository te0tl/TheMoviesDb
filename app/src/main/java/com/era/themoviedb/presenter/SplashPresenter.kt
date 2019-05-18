package com.era.themoviedb.presenter

import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.contract.MvpSplash


class SplashPresenter(private val model : MvpSplash.Model) : MvpSplash.Presenter {

    private var view : MvpSplash.View ? = null

    override fun onViewAvailable(view:  BaseView?) {
        this.view = view as MvpSplash.View
        this.view?.goToMovies()
        this.view?.destroyView()
    }
}