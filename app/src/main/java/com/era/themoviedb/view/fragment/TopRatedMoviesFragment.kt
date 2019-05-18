package com.era.themoviedb.view.fragment

import android.view.View
import com.era.themoviedb.R
import com.era.themoviedb.contract.movies.MvpMovies
import com.era.themoviedb.framework.di.KOIN_MAIN_MOVIES_SCOPE
import com.era.themoviedb.contract.movies.MvpTopRatedMovies
import com.era.themoviedb.view.adapter.MoviesAdapter
import com.era.themoviedb.view.common.BaseRecyclerViewAdapter
import com.era.themoviedb.view.entity.Movie
import kotlinx.android.synthetic.main.fragment_movie_list.*

class TopRatedMoviesFragment : BaseFragment<MvpTopRatedMovies.Presenter>(), MvpTopRatedMovies.View,
    BaseRecyclerViewAdapter.ItemClickListener<Movie>{

    override val fragmentResourceId = R.layout.fragment_movie_list

    override var scopeId = KOIN_MAIN_MOVIES_SCOPE

    override val presenter by lazy {
        scope.get<MvpTopRatedMovies.Presenter>()
    }

    private val presenterMovies by lazy {
        scope.get<MvpMovies.Presenter>()
    }

    private lateinit var moviesAdapter : MoviesAdapter

    override fun onPostCreateView(rootView: View) {
        super.onPostCreateView(rootView)

        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter
        moviesAdapter.itemClickListener = this

        swipeRefreshLayout.setOnRefreshListener {
            postDelayed(2) {
                swipeRefreshLayout.isRefreshing = false
            }
        }

        recyclerView.setupScrollListener {
            presenter.onRequestedMoreMovies()
        }
    }


    override fun onNewMovie(movie: Movie) {
        moviesAdapter.addItem(movie)
    }

    override fun onItemClick(item: Movie) {
        presenterMovies.onClickMovie(item)
    }

    override fun performSearch(query: String) {
        moviesAdapter.performSearch(query)
    }
}
