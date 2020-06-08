package com.te0tl.themoviesdb.domain.entities

import java.util.*

enum class Category {
    POPULAR, INCOMING, TOP_RATED,
}

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: Date,
    var posterUrl: String
)

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: Date,
    var backdropUrl: String,
    val homePage: String?
)
