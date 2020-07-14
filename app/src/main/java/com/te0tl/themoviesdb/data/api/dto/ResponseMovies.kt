package com.te0tl.themoviesdb.data.api.dto

import com.squareup.moshi.Json

data class ResponseMovies(@field:Json(name = "results") val movies : List<Movie>,
                          val page : Int)