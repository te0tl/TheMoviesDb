package com.te0tl.themoviesdb.data.api.dto

import com.squareup.moshi.Json

/**
 * @param pathUrl viene relativo de la API
 * @param releaseDate viene formato "YYYY-MM-dd"
 */
data class MovieDetail(val id: Int, val title : String, val overview : String,
                       @field:Json(name = "release_date") val releaseDate : String,
                       @field:Json(name = "backdrop_path") var pathUrl : String,
                       @field:Json(name = "original_title") val originalTitle : String,
                       @field:Json(name = "homepage") val homePage : String?)

/*
https://image.tmdb.org/t/p/w500/bk8LyaMqUtaQ9hUShuvFznQYQKR.jpg
        "vote_count": 304,
      "id": 456740,
      "video": false,
      "vote_average": 5.2,
      "title": "Hellboy",
      "popularity": 206.501,
      "poster_path": "/bk8LyaMqUtaQ9hUShuvFznQYQKR.jpg",
      "original_language": "en",
      "original_title": "Hellboy",
      "genre_ids": [
        28,
        12,
        14
      ],
      "backdrop_path": "/5BkSkNtfrnTuKOtTaZhl8avn4wU.jpg",
      "adult": false,
      "overview": "Hellboy comes to England, where he must defeat Nimue, Merlin's consort and the Blood Queen. But their battle will bring about the end of the world, a fate he desperately tries to turn away.",
      "release_date": "2019-04-10"
 */