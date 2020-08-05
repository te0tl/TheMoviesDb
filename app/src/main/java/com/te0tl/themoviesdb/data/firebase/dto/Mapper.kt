package com.te0tl.themoviesdb.data.firebase.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Comment
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import com.te0tl.themoviesdb.data.firebase.dto.Comment as CommentDto

object Mapper {

    fun dtoCommentToModel(queryDocumentSnapshot: QueryDocumentSnapshot): Comment {

        val comment =
            queryDocumentSnapshot.toObject(CommentDto::class.java)

        return Comment(comment.publisher, comment.comment, comment.date?.toDate())
    }

    fun modelToDto(comment: Comment) =
        CommentDto(comment.publisher, comment.comment, Timestamp(comment.date ?: Date()))

}