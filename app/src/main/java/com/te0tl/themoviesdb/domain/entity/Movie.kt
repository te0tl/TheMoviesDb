package com.te0tl.themoviesdb.domain.entity

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterUrl: String
)

