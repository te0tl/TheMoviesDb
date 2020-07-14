package com.te0tl.themoviesdb.domain.repository

import com.te0tl.commons.domain.Result
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.entity.Video

interface MoviesRepository {

    suspend fun getMovies(category: Category, page: Int) : Result<List<Movie>, String>

    suspend fun getMovie(category: Category, id: Int): Result<Movie, String>

    suspend fun getMovieDetail(id: Int, category: Category): Result<MovieDetail, String>

    suspend fun getMovieVideos(id: Int, category: Category): Result<List<Video>, String>

}
