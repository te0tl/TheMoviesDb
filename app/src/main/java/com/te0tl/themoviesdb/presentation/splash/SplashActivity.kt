package com.te0tl.themoviesdb.presentation.splash

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.te0tl.commons.presentation.activity.BaseViewModelActivity
import com.te0tl.themoviesdb.presentation.movies.MoviesHomeActivity
import org.jetbrains.anko.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseViewModelActivity<SplashState, SplashViewModel>() {

    override val standardViewCreation = false
    override val idViewResource = -1

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
        startActivity(intentFor<MoviesHomeActivity>())
    }

}