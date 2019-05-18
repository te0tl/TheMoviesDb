package com.era.themoviedb.view.entity

data class MovieDetail(val id: Int, val title : String, val overview : String,
                       val backdropUrl : String, val date : String?,
                       val originalTitle : String, val homePage : String?,
                       val youtubeVideos : List<YoutubeVideo>?)
