package com.era.themoviedb.view.activity

import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.era.themoviedb.R
import com.era.themoviedb.framework.di.KOIN_MAIN_MOVIES_SCOPE
import com.era.themoviedb.framework.extension.android.replaceFragment
import com.era.themoviedb.contract.movies.MvpMovies
import com.era.themoviedb.view.entity.Movie
import com.era.themoviedb.view.fragment.PopularMoviesFragment
import com.era.themoviedb.view.fragment.TopRatedMoviesFragment
import com.era.themoviedb.view.fragment.UpcomingMoviesFragment
import kotlinx.android.synthetic.main.activity_movies.*
import org.jetbrains.anko.intentFor

class MoviesActivity : BaseActivity<MvpMovies.Presenter>(), MvpMovies.View {
    override var showBackButton = false

    override var scopeId = KOIN_MAIN_MOVIES_SCOPE

    override val presenter by lazy {
        scope.get<MvpMovies.Presenter>()
    }

    override val activityResourceId = R.layout.activity_movies

    private val popularMoviesFragment : PopularMoviesFragment by lazy {
        PopularMoviesFragment()
    }

    private val topRatedMoviesFragment : TopRatedMoviesFragment by lazy {
        TopRatedMoviesFragment()
    }

    private val upcomingMoviesFragment : UpcomingMoviesFragment by lazy {
        UpcomingMoviesFragment()
    }

    override fun onPostCreateView() {
        super.onPostCreateView()
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_popular -> {
                    presenter.onShowPopularMoviesSelected()
                }
                R.id.navigation_top_rated -> {
                    presenter.onShowTopRatedMoviesSelected()
                }
                R.id.navigation_upcoming -> {
                    presenter.onShowUpcomingMoviesSelected()
                }

            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.action_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onQuerySubmit(query = newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.onQuerySubmit(query = query)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun showPopularMovies() {
        replaceFragment(popularMoviesFragment, R.id.fragmentContainer)
    }

    override fun showTopRatedMovies() {
        replaceFragment(topRatedMoviesFragment, R.id.fragmentContainer)
    }

    override fun showUpcomingMovies() {
        replaceFragment(upcomingMoviesFragment, R.id.fragmentContainer)
    }

    override fun gotoDetailMovie(movie: Movie) {
        startActivity(intentFor<MovieDetailActivity>(
            MovieDetailActivity.MOVIE_ID to movie.id,
            MovieDetailActivity.MOVIE_CATEGORY to movie.category.toString()))
    }
}
