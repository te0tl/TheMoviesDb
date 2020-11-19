package com.te0tl.themoviesdb.domain.entity

import java.util.*


data class Comment(
    val id: String = UNSET_ID,
    val publisher: String,
    val comment: String,
    val date: Date = Date()
)