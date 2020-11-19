package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.common.domain.BaseUseCaseWithParam
import com.te0tl.common.domain.Res
import com.te0tl.common.platform.extension.safeMessage
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.repository.MoviesRepository

class GetMoviesUseCase(private val moviesRepository: MoviesRepository) :
    BaseUseCaseWithParam<Category, List<Movie>, String>() {

    private var currentPage = 0

    override suspend fun execute(param: Category): Res<List<Movie>, String> {
        return try {
            currentPage++

            when (val result = moviesRepository.getMovies(param, currentPage)) {

                is Res.Success -> {
                    Res.Success(result.data)
                }

                is Res.Failure -> {
                    Res.Failure(result.error)
                }
            }

        } catch (e: Exception) {
            Res.Failure(e.safeMessage)
        }
    }

}
