package com.te0tl.themoviesdb.platform.di

import com.te0tl.themoviesdb.data.api.TmdbApi
import com.te0tl.themoviesdb.data.repository.CommentsRepositoryImpl
import com.te0tl.themoviesdb.data.repository.MoviesRepositoryImpl
import com.te0tl.themoviesdb.domain.repository.CommentsRepository
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import com.te0tl.themoviesdb.domain.usecase.*
import com.te0tl.themoviesdb.presentation.comments.CommentsViewModel
import com.te0tl.themoviesdb.presentation.movie.MovieViewModel
import com.te0tl.themoviesdb.presentation.movies.MoviesViewModel
import com.te0tl.themoviesdb.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

}

val repositoriesModule = module {
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }
    single<CommentsRepository> { CommentsRepositoryImpl() }

}

val useCasesModule = module {
    factory { GetMoviesUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }
    factory { GetCommentsUseCase(get()) }
    factory { AddCommentUseCase(get()) }
    factory { GetCommentFormValidatorUseCase() }

}

val viewModelsModule = module {
    viewModel { SplashViewModel() }
    viewModel { MoviesViewModel(get(), get()) }
    viewModel { MovieViewModel(get(), get()) }
    viewModel { CommentsViewModel(get(), get(), get(), get()) }

}

val retrofitModule = module {
    single { TmdbApi.retrofit }

}
