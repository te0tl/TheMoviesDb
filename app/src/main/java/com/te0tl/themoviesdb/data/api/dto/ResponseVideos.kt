package com.te0tl.themoviesdb.data.api.dto

import com.squareup.moshi.Json

data class ResponseVideos(@field:Json(name = "results") val videos : List<Video>)