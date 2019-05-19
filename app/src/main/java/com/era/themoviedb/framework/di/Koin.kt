package com.era.themoviedb.framework.di

import com.era.themoviedb.model.data.service.Retrofit
import com.era.themoviedb.model.usecase.movies.MoviesModel
import com.era.themoviedb.model.usecase.movies.PopularMoviesModel
import com.era.themoviedb.model.usecase.movies.TopRatedMoviesModel
import com.era.themoviedb.model.usecase.movies.UpcomingMoviesModel
import com.era.themoviedb.model.data.repository.MoviesRepository
import com.era.themoviedb.model.data.repository.MoviesRepositoryTheMovieDbOrg
import com.era.themoviedb.model.data.service.TheMoviesDbApi
import com.era.themoviedb.model.usecase.MovieDetailModel
import com.era.themoviedb.model.usecase.SplashModel
import com.era.themoviedb.contract.*
import com.era.themoviedb.contract.movies.*
import com.era.themoviedb.framework.rx.AndroidDisposable
import com.era.themoviedb.presenter.*
import com.era.themoviedb.presenter.movies.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

/*
Application's context is available globally:
val appContext by inject<Context>()
 */
val appModule = module {
    single <TheMoviesDbApi> { Retrofit.retrofitMovies.create(TheMoviesDbApi::class.java) }
    single <MoviesRepository> { MoviesRepositoryTheMovieDbOrg() }
    factory <AndroidDisposable> { AndroidDisposable() }
}

val splashModule = module {
    factory <MvpSplash.Model> { SplashModel() }
    factory <MvpSplash.Presenter> { SplashPresenter(get(), get()) }
}

const val KOIN_MAIN_MOVIES_SCOPE = "KOIN_MAIN_MOVIES_SCOPE"
val moviesModule = module {
    scope(named(KOIN_MAIN_MOVIES_SCOPE)) {
        scoped <MvpMovies.Model> { MoviesModel() }
        scoped <MvpPopularMovies.Model> { PopularMoviesModel() }
        scoped <MvpTopRatedMovies.Model> { TopRatedMoviesModel() }
        scoped <MvpUpcomingMovies.Model> { UpcomingMoviesModel() }

        scoped <MvpPopularMovies.Presenter> { PopularMoviesPresenter(get(), get()) }
        scoped <MvpTopRatedMovies.Presenter> { TopRatedMoviesPresenter(get(), get()) }
        scoped <MvpUpcomingMovies.Presenter> { UpcomingMoviesPresenter(get(), get()) }

        scoped <MvpMovies.Presenter> { MoviesPresenter(get(), get(), get(), get(), get()) }
    }
}

val movieDetailModule = module {
    factory <MvpMovieDetail.Model> { MovieDetailModel() }
    factory <MvpMovieDetail.Presenter> { MovieDetailPresenter(get(), get()) }
}