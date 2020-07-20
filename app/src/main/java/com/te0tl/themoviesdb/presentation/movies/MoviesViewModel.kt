package com.te0tl.themoviesdb.presentation.movies

import android.content.Context
import com.te0tl.commons.domain.Result
import com.te0tl.commons.domain.ifFailure
import com.te0tl.commons.domain.ifSuccess
import com.te0tl.commons.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.usecase.GetMoviesUseCase
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.coroutines.launch

class MoviesViewModel(private val context: Context, private val getMoviesUseCase: GetMoviesUseCase) :
    BaseViewModel<MoviesState>() {

    fun getMovies(category: Category, showLoading: Boolean = true) {
        uiScope.launch {

            if (showLoading)
                updateViewModelState(MoviesState.Loading)

            val result: Result<List<Movie>, String> = getMoviesUseCase(category)

            result.ifSuccess {
                updateViewModelState(MoviesState.MoviesReady(it))
            }

            result.ifFailure {
                updateViewModelState(MoviesState.Error(it))
            }

        }
    }

    fun getMoreMovies(category: Category) {
        getMovies(category, false)
    }

}

sealed class MoviesState {
    data class MoviesReady(val movies: List<Movie>): MoviesState()
    object Loading: MoviesState()
    data class Error(val error: String): MoviesState()
}
