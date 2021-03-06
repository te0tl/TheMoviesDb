package com.te0tl.themoviesdb.presentation.movies

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.te0tl.common.platform.extension.android.*
import com.te0tl.common.presentation.activity.BaseActivity
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.ActivityMoviesBinding
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.presentation.AboutUsActivity
import com.te0tl.themoviesdb.presentation.comments.CommentsActivity
import com.te0tl.themoviesdb.presentation.movie.*
import org.jetbrains.anko.intentFor

const val EVENT_KEY_TOOLBAR = "EVENT_KEY_TOOLBAR"
const val RESULT_KEY_PROGRESS = "RESULT_KEY_PROGRESS"
const val RESULT_KEY_ERROR = "RESULT_KEY_ERROR"

class MoviesActivity : BaseActivity<ActivityMoviesBinding>() {

    override val viewBinding: ActivityMoviesBinding by lazy {
        ActivityMoviesBinding.inflate(layoutInflater)
    }

    override var showBackButton = false

    private lateinit var currentFragment: MoviesFragment

    private val popularMoviesFragment: MoviesFragment by lazy {
        MoviesFragment().apply {
            arguments = bundleOf(ARGUMENT_KEY_CATEGORY to Category.POPULAR)
        }
    }

    private val topRatedMoviesFragment: MoviesFragment by lazy {
        MoviesFragment().apply {
            arguments = bundleOf(ARGUMENT_KEY_CATEGORY to Category.TOP_RATED)
        }
    }

    private val upcomingMoviesFragment: MoviesFragment by lazy {
        MoviesFragment().apply {
            arguments = bundleOf(ARGUMENT_KEY_CATEGORY to Category.UPCOMING)
        }
    }

    override fun onViewAndExtrasReady() {
        setupFragmentsNavigation()
        setupNavigation()
        setFragmentResultListener(
            EVENT_KEY_TOOLBAR, {
                onToolbarInfo(it)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_actionbar_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.action_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                supportFragmentManager.setFragmentResult(
                    EVENT_SEARCH_KEY,
                    bundleOf(RESULT_SEARCH_KEY to newText)
                )
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupFragmentsNavigation() {
        currentFragment = popularMoviesFragment

        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_popular -> {
                    toggleFragment(
                        R.id.fragmentContainer,
                        "pop",
                        currentFragment,
                        popularMoviesFragment
                    )
                    currentFragment = popularMoviesFragment

                }
                R.id.navigation_top_rated -> {
                    toggleFragment(
                        R.id.fragmentContainer,
                        "top",
                        currentFragment,
                        topRatedMoviesFragment
                    )
                    currentFragment = topRatedMoviesFragment
                }
                R.id.navigation_upcoming -> {
                    toggleFragment(
                        R.id.fragmentContainer,
                        "up",
                        currentFragment,
                        upcomingMoviesFragment
                    )
                    currentFragment = upcomingMoviesFragment
                }
            }
            true
        }
        viewBinding.bottomNavigationView.selectedItemId = R.id.navigation_popular
    }

    private fun setupNavigation() {
        val toggle = ActionBarDrawerToggle(
            this, viewBinding.drawerLayout, viewBinding.includedToolbar.toolbar, 0, 0
        )
        viewBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        viewBinding.navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_comments -> {
                    startActivity(
                        intentFor<CommentsActivity>()
                    )
                }
                R.id.nav_about_us -> {
                    startActivity(
                        intentFor<AboutUsActivity>()
                    )
                }
            }
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        })

    }

    fun intentToDetailMovie(movie: Movie, category: Category) {
        startActivity(
            intentFor<MovieActivity>(
                EXTRA_KEY_MOVIE_ID to movie.id,
                EXTRA_KEY_MOVIE_TITLE to movie.title,
                EXTRA_KEY_MOVIE_POSTER_URL to movie.posterUrl

            )
        )
    }

    private fun onToolbarInfo(it: Bundle) {
        val errorMsg = it.getString(RESULT_KEY_ERROR) ?: ""

        viewBinding.includedToolbar.errorBar.setVisible(errorMsg != "")
        viewBinding.includedToolbar.errorBar.text = errorMsg

        viewBinding.includedToolbar.progressBar.setVisible(
            it.getBoolean(RESULT_KEY_PROGRESS) ?: false
        )
    }
}