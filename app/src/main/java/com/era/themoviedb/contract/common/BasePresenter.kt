package com.era.themoviedb.contract.common

import com.era.themoviedb.framework.rx.AndroidDisposable

interface BasePresenter {

    val disposables: AndroidDisposable

    fun onViewAvailable(view: BaseView?)

    fun onViewDestroyed() {
        disposables.dispose()
    }

    /**
     * The presenter takes the responsibility of destroying the view
     * on the back pressed event
     */
    fun onButtonBackPressed(view: BaseView?) {
        view?.destroyView()
    }
}