package com.era.themoviedb.view.activity

import android.os.Build
import android.view.WindowManager
import com.era.themoviedb.contract.MvpSplash
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<MvpSplash.Presenter>(), MvpSplash.View {

    override val presenter by inject<MvpSplash.Presenter>()

    override val activityResourceId = -1

    override fun onPostCreateView() {
        super.onPostCreateView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    override fun goToMovies() {
        startActivity(intentFor<MoviesActivity>())
    }
}