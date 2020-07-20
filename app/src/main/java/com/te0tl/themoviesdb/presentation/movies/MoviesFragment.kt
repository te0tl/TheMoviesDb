package com.te0tl.themoviesdb.presentation.movies

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import com.te0tl.commons.platform.extension.android.hide
import com.te0tl.commons.platform.extension.android.setFragmentResultListener
import com.te0tl.commons.platform.extension.android.show
import com.te0tl.commons.presentation.fragment.BaseViewModelFragment
import com.te0tl.commons.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.themoviesdb.databinding.FragmentMoviesBinding
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.platform.logging.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ARGUMENT_KEY_CATEGORY = "ARGUMENT_KEY_CATEGORY"
const val RESULT_QUERY_KEY = "RESULT_QUERY_KEY"
const val ARGUMENT_QUERY_KEY = "ARGUMENT_QUERY_KEY"

class MoviesFragment :
    BaseViewModelFragment<FragmentMoviesBinding, MoviesState, MoviesViewModel>(),
    BaseRecyclerViewAdapter.ItemClickListener<Movie> {

    override fun getViewBinding(parent: ViewGroup) =
        FragmentMoviesBinding.inflate(layoutInflater, parent, false)

    override val viewModel: MoviesViewModel by viewModel()

    private lateinit var category: Category

    private lateinit var moviesAdapter: MoviesAdapter

    override fun parseArguments(bundle: Bundle?) {
        checkNotNull(bundle) {
            "arguments can't be null"
        }
        bundle.run {
            require(containsKey(ARGUMENT_KEY_CATEGORY))
            checkNotNull(getSerializable(ARGUMENT_KEY_CATEGORY))
            category = getSerializable(ARGUMENT_KEY_CATEGORY) as Category
        }
    }

    override fun onViewAndExtrasReady() {
        setFragmentResultListener(
            RESULT_QUERY_KEY,
            FragmentResultListener { requestKey, result ->
                onFragmentResult(requestKey, result)
            })

        //disable swipe to refresh
        viewBinding.swipeRefreshLayout.setDistanceToTriggerSync(Int.MAX_VALUE)

        moviesAdapter = MoviesAdapter()
        moviesAdapter.itemClickListener = this

        viewBinding.recyclerView.adapter = moviesAdapter
        viewBinding.recyclerView.setupScrollListener {
            viewModel.getMoreMovies(category)
        }

        viewModel.getMovies(this.category)

    }

    override fun onNewViewModelState(newState: MoviesState) {
        with(viewBinding) {
            when (newState) {
                is MoviesState.MoviesReady -> {
                    contentContainer.show()
                    progressContainer.hide()
                    errorContainer.hide()
                    moviesAdapter.addItems(newState.movies)
                }
                is MoviesState.Loading -> {
                    contentContainer.hide()
                    progressContainer.show()
                    errorContainer.hide()
                }
                is MoviesState.Error -> {
                    contentContainer.hide()
                    progressContainer.hide()
                    errorContainer.show()
                    txtViewError.text = newState.error
                }
            }
        }
    }

    override fun onItemClick(item: Movie) {
        (activity as MoviesActivity).intentToDetailMovie(item, category)
    }

    private fun onFragmentResult(requestKey: String, result: Bundle) {
        check(RESULT_QUERY_KEY == requestKey)

        result.getString(ARGUMENT_QUERY_KEY)?.also {
            moviesAdapter.performSearch(it)
        }

    }

}