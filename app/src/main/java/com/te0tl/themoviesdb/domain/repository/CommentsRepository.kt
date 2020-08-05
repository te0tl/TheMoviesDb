package com.te0tl.themoviesdb.domain.repository

import com.te0tl.commons.domain.Result
import com.te0tl.themoviesdb.domain.entity.Comment
import kotlinx.coroutines.flow.Flow


interface CommentsRepository {

    fun getComments() : Flow<Result<Comment, String>>

    suspend fun publishComment(comment: Comment)

}