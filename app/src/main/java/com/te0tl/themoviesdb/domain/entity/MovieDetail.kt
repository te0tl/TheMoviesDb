package com.te0tl.themoviesdb.domain.entity

import java.util.*

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    var backdropUrl: String,
    val homePage: String?,
    val youtubeVideos: List<YoutubeVideo>
)

