package com.era.themoviedb.model.data.repository.entity

import com.squareup.moshi.Json

data class ResponseMovies(@field:Json(name = "results") val movies : List<Movie>,
                          val page : Int)