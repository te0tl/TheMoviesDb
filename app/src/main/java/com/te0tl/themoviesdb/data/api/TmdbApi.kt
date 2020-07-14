package com.te0tl.themoviesdb.data.api

import com.te0tl.themoviesdb.data.api.dto.MovieDetail
import com.te0tl.themoviesdb.data.api.dto.ResponseMovies
import com.te0tl.themoviesdb.data.api.dto.ResponseVideos
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "b2765287e51c23623756041dfb69eba0"

const val BASE_URL_IMAGES = "https://image.tmdb.org/t/p/"

interface TmdbApi {

    companion object {
        private val loggingInterceptor by lazy {
            HttpLoggingInterceptor().apply {
                setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            }
        }

        private val authenticationInterceptor by lazy {
            Interceptor { chain ->
                with(chain) {
                    val authenticatedUrl = request().url
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()

                    val authenticatedRequest = request()
                        .newBuilder()
                        .url(authenticatedUrl)
                        .build()

                    proceed(authenticatedRequest)
                }
            }
        }

        val retrofit: TmdbApi by lazy {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(TmdbApi::class.java)
        }
    }

    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("page") page: Int
    ): ResponseMovies

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int
    ): MovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int
    ): ResponseVideos

}