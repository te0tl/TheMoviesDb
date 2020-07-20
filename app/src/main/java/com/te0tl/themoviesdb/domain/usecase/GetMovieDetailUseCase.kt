package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.commons.domain.BaseUseCase
import com.te0tl.commons.domain.BaseUseCaseWithParam
import com.te0tl.commons.domain.Result
import com.te0tl.commons.platform.extension.safeMessage
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.coroutines.delay

class GetMovieDetailUseCase(private val moviesRepository: MoviesRepository) :
    BaseUseCaseWithParam<Int, MovieDetail, String>() {


    override suspend fun execute(param: Int): Result<MovieDetail, String> {
        return try {

            when (val result = moviesRepository.getMovieDetail(param)) {
                is Result.Success -> {
                    Result.Success(result.data)
                }

                is Result.Failure -> {
                    Result.Failure(result.error)
                }
            }

        } catch (e: Exception) {
            Result.Failure(e.safeMessage)
        }
    }

}
