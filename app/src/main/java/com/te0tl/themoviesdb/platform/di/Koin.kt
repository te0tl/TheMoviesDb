package com.te0tl.themoviesdb.platform.di

import com.te0tl.themoviesdb.presentation.movies.MoviesHomeViewModel
import com.te0tl.themoviesdb.presentation.movies.MoviesListViewModel
import com.te0tl.themoviesdb.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

}

val repositoriesModule = module {

}

val useCasesModule = module {


}

val viewModelsModule = module {

    viewModel { SplashViewModel() }
    viewModel { MoviesHomeViewModel() }
    viewModel { MoviesListViewModel() }

}

val retrofitModule = module {


}
