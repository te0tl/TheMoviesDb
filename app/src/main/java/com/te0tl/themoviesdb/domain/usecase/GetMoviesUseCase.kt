package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.commons.domain.BaseUseCase
import com.te0tl.commons.domain.BaseUseCaseWithParam
import com.te0tl.commons.domain.Result
import com.te0tl.commons.platform.extension.safeMessage
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.repository.MoviesRepository
import kotlinx.coroutines.delay

class GetMoviesUseCase(private val moviesRepository: MoviesRepository) :
    BaseUseCaseWithParam<Category, List<Movie>, String>() {

    override suspend fun execute(param: Category): Result<List<Movie>, String> {
        return try {
            delay(2000)
            when (val result = moviesRepository.getMovies(param, 1)) {
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
