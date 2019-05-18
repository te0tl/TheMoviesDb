package com.era.themoviedb.framework.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AndroidDisposable {
    private val delegateDisposables = lazy {
        CompositeDisposable()
    }

    private val disposables by delegateDisposables


    fun add(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (delegateDisposables.isInitialized()) {
            disposables.clear()
        }
    }
}
