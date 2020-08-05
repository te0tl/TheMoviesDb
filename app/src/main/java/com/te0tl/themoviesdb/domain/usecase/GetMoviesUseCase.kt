package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.commons.domain.BaseUseCase
import com.te0tl.commons.domain.BaseUseCaseWithParam
import com.te0tl.commons.domain.Result
import com.te0tl.commons.platform.extension.safeMessage
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.coroutines.delay
import java.lang.Error

class GetMoviesUseCase(private val moviesRepository: MoviesRepository) :
    BaseUseCaseWithParam<Category, List<Movie>, String>() {

    private var currentPage = 0

    override suspend fun execute(param: Category): Result<List<Movie>, String> {
        return try {
            currentPage++

            when (val result = moviesRepository.getMovies(param, currentPage)) {

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
