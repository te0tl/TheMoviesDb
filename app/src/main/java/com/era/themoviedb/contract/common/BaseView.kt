package com.era.themoviedb.contract.common

interface BaseView {

    fun showMessage(message: String)

    fun showErrorMessage(message: String)

    fun showProgress()

    fun stopShowingProgress()

    fun postDelayed(seconds : Int, block : () -> Unit)

    fun destroyView()

}