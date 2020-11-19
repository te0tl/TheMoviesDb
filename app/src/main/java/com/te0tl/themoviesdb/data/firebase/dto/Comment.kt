package com.te0tl.themoviesdb.data.firebase.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.te0tl.common.data.firebase.FirebaseEntity

import com.te0tl.themoviesdb.domain.entity.Comment as CommentDomain
class Comment(
    @DocumentId
    override var idFirebase: String? = null,
    var publisher: String = "",
    var comment: String = "",
    var date: Timestamp? = null
) : FirebaseEntity

fun Comment.toDomain() =
    CommentDomain(idFirebase!!, publisher, comment, date!!.toDate())

fun CommentDomain.toFirebaseDto() =
    Comment(id, publisher, comment, Timestamp(date))
