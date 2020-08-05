package com.te0tl.themoviesdb

import android.app.Application
import android.os.Handler
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.te0tl.commons.domain.Result
import com.te0tl.themoviesdb.domain.repository.CommentsRepository
import com.te0tl.themoviesdb.platform.di.*
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.te0tl.themoviesdb.data.repository.CommentsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

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
