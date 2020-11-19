package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.common.domain.BaseUseCaseFlow
import com.te0tl.common.domain.ResDef
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class GetCommentsUseCase(private val commentsRepository: CommentsRepository) :
    BaseUseCaseFlow<Comment>() {

    override fun execute(): Flow<ResDef<Comment>> = flow {

        commentsRepository.flowAll()
            .collect {
                when (it) {
                    is ResDef.Success -> {
                        emit(ResDef.Success(it.data))
                    }
                    is ResDef.Failure -> {
                        emit(ResDef.Failure(it.error))
                    }
                }
            }

    }.flowOn(Dispatchers.Default)

}
