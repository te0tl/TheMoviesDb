package com.te0tl.themoviesdb.presentation.movies

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.te0tl.common.platform.extension.android.sendFragmentResult
import com.te0tl.common.platform.extension.android.setFragmentResultListener
import com.te0tl.common.presentation.fragment.BaseViewModelFragment
import com.te0tl.common.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.themoviesdb.databinding.FragmentMoviesBinding
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.domain.entity.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ARGUMENT_KEY_CATEGORY = "ARGUMENT_KEY_CATEGORY"
const val EVENT_SEARCH_KEY = "RESULT_SEARCH_KEY"
const val RESULT_SEARCH_KEY = "RESULT_SEARCH_KEY"

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
            EVENT_SEARCH_KEY,
            {
                onNewSearch(it)
            }
        )

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
                    moviesAdapter.addItems(newState.movies)
                    sendFragmentResult(
                        EVENT_KEY_TOOLBAR,
                        bundleOf(RESULT_KEY_PROGRESS to false, RESULT_KEY_ERROR to "")
                    )
                }
                is MoviesState.Loading -> {
                    sendFragmentResult(
                        EVENT_KEY_TOOLBAR,
                        bundleOf(RESULT_KEY_PROGRESS to true, RESULT_KEY_ERROR to "")
                    )
                }
                is MoviesState.Error -> {
                    sendFragmentResult(
                        EVENT_KEY_TOOLBAR,
                        bundleOf(RESULT_KEY_PROGRESS to false, RESULT_KEY_ERROR to newState.error)
                    )
                }
            }
        }
    }

    override fun onItemClick(item: Movie) {
        (activity as MoviesActivity).intentToDetailMovie(item, category)
    }

    private fun onNewSearch(result: Bundle) {
        result.getString(RESULT_SEARCH_KEY)?.also {
            moviesAdapter.performSearch(it)
        }

    }

}