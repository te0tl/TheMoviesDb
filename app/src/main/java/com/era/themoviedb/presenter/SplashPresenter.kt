package com.era.themoviedb.presenter

import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.contract.MvpSplash
import com.era.themoviedb.framework.rx.AndroidDisposable


class SplashPresenter(private val model : MvpSplash.Model, override val disposables : AndroidDisposable) : MvpSplash.Presenter {

    private var view : MvpSplash.View ? = null

    override fun onViewAvailable(view:  BaseView?) {
        this.view = view as MvpSplash.View
        this.view?.goToMovies()
        this.view?.destroyView()
    }
}