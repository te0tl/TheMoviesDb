package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.common.domain.BaseUseCaseWithParam
import com.te0tl.common.domain.Res
import com.te0tl.common.platform.extension.safeMessage
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.repository.CommentsRepository

class AddCommentUseCase(private val commentsRepository: CommentsRepository) :
    BaseUseCaseWithParam<Comment, Unit, String>() {

    override suspend fun execute(param: Comment): Res<Unit, String> {

        return try {
            commentsRepository.create(param)
            Res.Success(Unit)
        } catch (e: Exception) {
            Res.Failure(e.safeMessage)
        }


    }

}
