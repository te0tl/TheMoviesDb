package com.te0tl.themoviesdb.presentation.movie

import android.content.Context
import com.te0tl.common.domain.Res
import com.te0tl.common.domain.ifFailure
import com.te0tl.common.domain.ifSuccess
import com.te0tl.common.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.usecase.GetMovieDetailUseCase
import kotlinx.coroutines.launch

class MovieViewModel(private val context: Context, private val getMovieDetailUseCase: GetMovieDetailUseCase) :
    BaseViewModel<MovieState>() {

    fun getMovieDetail(id: Int) {
        uiScope.launch {

            updateViewModelState(MovieState.Loading)

            val res: Res<MovieDetail, String> = getMovieDetailUseCase(id)

            res.ifSuccess {
                updateViewModelState(MovieState.MovieDetailReady(it))
            }

            res.ifFailure {
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
