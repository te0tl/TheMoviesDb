package com.te0tl.themoviesdb.presentation.splash

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.te0tl.commons.presentation.activity.BaseViewModelActivity
import com.te0tl.themoviesdb.presentation.movies.MoviesActivity
import org.jetbrains.anko.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseViewModelActivity<Nothing, SplashState, SplashViewModel>() {

    @Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER")
    override val viewBinding: Nothing by lazy { throw IllegalArgumentException() }

    override val standardViewBinding = false

    override val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }

    }

    override fun onNewViewModelState(newState: SplashState) {
        when(newState) {
            is SplashState.GoToMovies -> this.goToMovies()
        }
    }

    private fun goToMovies() {
        finish()
        startActivity(intentFor<MoviesActivity>())
    }

}