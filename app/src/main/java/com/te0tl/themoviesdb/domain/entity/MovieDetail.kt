package com.te0tl.themoviesdb.domain.entity

import java.util.*

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: Date,
    var backdropUrl: String,
    val homePage: String?
)

