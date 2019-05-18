package com.era.themoviedb.model.data.service

import com.era.themoviedb.model.data.repository.entity.MovieDetail
import com.era.themoviedb.model.data.repository.entity.ResponseMovies
import com.era.themoviedb.model.data.repository.entity.ResponseVideos
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMoviesDbApi {

    @GET("movie/{category}") // popular top_rated upcoming
    fun getMovies(@Path("category") category : String,
                  @Query("page") page : Int,
                  @Query("api_key") apiKey : String = Retrofit.apiKey) : Single<ResponseMovies>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId : Int,
                 @Query("api_key") apiKey : String = Retrofit.apiKey) : Single<MovieDetail>

    @GET("movie/{movie_id}/videos")
    fun getVideos(@Path("movie_id") movieId : Int,
                  @Query("api_key") apiKey : String = Retrofit.apiKey) : Single<ResponseVideos>


}