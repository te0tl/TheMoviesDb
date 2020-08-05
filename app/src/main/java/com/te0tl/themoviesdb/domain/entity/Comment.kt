package com.te0tl.themoviesdb.domain.entity

import java.util.*


data class Comment(val publisher: String, val comment: String, val date: Date? = null)