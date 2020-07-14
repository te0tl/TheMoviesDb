package com.te0tl.themoviesdb.platform.di

import com.te0tl.themoviesdb.data.api.TmdbApi
import com.te0tl.themoviesdb.data.repository.MoviesRepositoryImpl
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import com.te0tl.themoviesdb.domain.usecase.GetMoviesUseCase
import com.te0tl.themoviesdb.presentation.movies.MoviesViewModel
import com.te0tl.themoviesdb.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

}

val repositoriesModule = module {
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }

}

val useCasesModule = module {
    factory { GetMoviesUseCase(get()) }
}

val viewModelsModule = module {
    viewModel { SplashViewModel() }
    viewModel { MoviesViewModel(get(), get()) }

}

val retrofitModule = module {
    single { TmdbApi.retrofit }

}
