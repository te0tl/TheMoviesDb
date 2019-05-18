package com.era.themoviedb.model.data.repository

import com.era.themoviedb.model.data.repository.base.Repository
import com.era.themoviedb.model.data.repository.entity.Movie
import com.era.themoviedb.model.data.repository.entity.MovieDetail
import com.era.themoviedb.model.data.repository.entity.Video
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesRepository : Repository {

    fun getMovies(category: String, page: Int) : Observable<Movie>

    fun getMovie(id: Int, category: String): Single<Movie>

    fun getMovieDetail(id: Int, category: String): Single<MovieDetail>

    fun getMovieVideos(id: Int, category: String): Single<List<Video>>
}
