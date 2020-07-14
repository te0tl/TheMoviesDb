package com.te0tl.themoviesdb

import android.app.Application
import com.te0tl.themoviesdb.platform.di.*
import com.te0tl.themoviesdb.platform.logging.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.setupLogger(this)

        setupKoin()

    }

    private fun setupKoin() {

        startKoin {

            if (BuildConfig.DEBUG)
                androidLogger(Level.DEBUG)

            androidContext(this@App)

            modules(
                listOf(
                    appModule, retrofitModule, repositoriesModule,
                    viewModelsModule, useCasesModule
                )
            )
        }

    }
}
