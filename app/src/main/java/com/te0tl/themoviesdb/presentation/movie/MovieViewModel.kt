package com.te0tl.themoviesdb.presentation.movie

import android.content.Context
import com.te0tl.commons.domain.Result
import com.te0tl.commons.domain.ifFailure
import com.te0tl.commons.domain.ifSuccess
import com.te0tl.commons.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.usecase.GetMovieDetailUseCase
import com.te0tl.themoviesdb.domain.usecase.GetMoviesUseCase
import com.te0tl.themoviesdb.platform.logging.Logger
import com.te0tl.themoviesdb.presentation.movies.MoviesState
import kotlinx.coroutines.launch

class MovieViewModel(private val context: Context, private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    BaseViewModel<MovieState>() {

    fun getMovieDetail(id: Int) {
        uiScope.launch {

            updateViewModelState(MovieState.Loading)

            val result: Result<MovieDetail, String> = getMovieDetailUseCase(id)

            result.ifSuccess {
                updateViewModelState(MovieState.MovieDetailReady(it))
            }

            result.ifFailure {
                updateViewModelState(MovieState.Error(it))
            }

        }
    }

}

sealed class MovieState {
    data class MovieDetailReady(val movieDetail: MovieDetail): MovieState()
    object Loading: MovieState()
    data class Error(val error: String): MovieState()
}
