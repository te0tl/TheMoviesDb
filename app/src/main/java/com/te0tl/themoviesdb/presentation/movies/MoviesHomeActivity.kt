package com.te0tl.themoviesdb.presentation.movies

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.te0tl.commons.platform.extension.android.toggleFragment
import com.te0tl.commons.presentation.activity.BaseActivity
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.domain.entities.Category
import kotlinx.android.synthetic.main.activity_home_movies.*

class MoviesHomeActivity : BaseActivity() {

    override val idViewResource = R.layout.activity_home_movies

    override var showBackButton = false

    private lateinit var currentFragment: MoviesListFragment

    private val popularMoviesFragment: MoviesListFragment by lazy {
        MoviesListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARGUMENT_KEY_CATEGORY, Category.POPULAR)
            }
        }
    }

    private val topRatedMoviesFragment: MoviesListFragment by lazy {
        MoviesListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARGUMENT_KEY_CATEGORY, Category.TOP_RATED)
            }
        }
    }

    private val upcomingMoviesFragment: MoviesListFragment by lazy {
        MoviesListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARGUMENT_KEY_CATEGORY, Category.INCOMING)
            }
        }
    }

    override fun onViewAndExtrasReady() {
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_home_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.action_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                currentFragment.onQuerySubmit(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupNavigation() {
        currentFragment = popularMoviesFragment

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
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
        bottomNavigationView.selectedItemId = R.id.navigation_popular
    }
}