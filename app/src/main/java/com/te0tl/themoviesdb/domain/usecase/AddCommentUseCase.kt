package com.te0tl.themoviesdb.domain.usecase

import com.google.firebase.firestore.DocumentChange
import com.te0tl.commons.domain.BaseUseCase
import com.te0tl.commons.domain.BaseUseCaseFlow
import com.te0tl.commons.domain.BaseUseCaseWithParam
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

class AddCommentUseCase(private val commentsRepository: CommentsRepository) :
    BaseUseCaseWithParam<Comment, Unit, String>() {

    override suspend fun execute(param: Comment): Result<Unit, String> {

        return try {
            commentsRepository.publishComment(param)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e.safeMessage)
        }


    }

}
