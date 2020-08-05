package com.te0tl.themoviesdb.domain.usecase

import com.google.firebase.firestore.DocumentChange
import com.te0tl.commons.domain.BaseUseCaseFlow
import com.te0tl.commons.domain.Result
import com.te0tl.commons.platform.extension.safeMessage
import com.te0tl.themoviesdb.data.firebase.dto.Mapper
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.repository.CommentsRepository
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class GetCommentsUseCase(private val commentsRepository: CommentsRepository) :
    BaseUseCaseFlow<Comment, String>() {

    override fun execute(): Flow<Result<Comment, String>> = callbackFlow {

        commentsRepository.getComments()
            .collect {
                when (it) {
                    is Result.Success -> {
                        offer(Result.Success(it.data))
                    }
                    is Result.Failure -> {
                        offer(Result.Failure(it.error))
                    }
                }
            }

    }.flowOn(Dispatchers.Default)

}
