package com.era.themoviedb.presenter

import com.era.themoviedb.contract.MvpSplash
import com.era.themoviedb.framework.di.splashModule
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject


class SplashPresenterTest : AutoCloseKoinTest() {

    private val view : MvpSplash.View = mock()
    private val presenter : MvpSplash.Presenter by inject()

    /* Given */
    @Before
    fun setup() {
        startKoin {
            printLogger(Level.DEBUG)
            modules(splashModule)
        }


    }

    @Test
    fun flowSplash() {
        /* When */
        presenter.onViewAvailable(view)

        /* Then */
        inOrder (view){
            verify(view, times(1)).goToMovies()
            verify(view, times(1)).destroyView()
        }


    }
}