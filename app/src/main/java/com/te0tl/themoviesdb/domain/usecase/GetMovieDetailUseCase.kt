package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.common.domain.BaseUseCaseWithParam
import com.te0tl.common.domain.Res
import com.te0tl.common.platform.extension.safeMessage
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.repository.MoviesRepository

class GetMovieDetailUseCase(private val moviesRepository: MoviesRepository) :
    BaseUseCaseWithParam<Int, MovieDetail, String>() {


    override suspend fun execute(param: Int): Res<MovieDetail, String> {
        return try {

            when (val result = moviesRepository.getMovieDetail(param)) {
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
