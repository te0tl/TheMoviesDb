package com.era.themoviedb.model.data.repository.entity

import com.squareup.moshi.Json

data class ResponseVideos(@field:Json(name = "results") val videos : List<Video>)