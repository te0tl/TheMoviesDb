package com.te0tl.themoviesdb.domain.repository

import com.te0tl.common.domain.Res
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.domain.entity.MovieDetail

interface MoviesRepository {

    suspend fun getMovies(category: Category, page: Int) : Res<List<Movie>, String>

    suspend fun getMovieDetail(id: Int): Res<MovieDetail, String>

}
