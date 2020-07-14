package com.te0tl.themoviesdb.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import com.te0tl.commons.platform.extension.android.hide
import com.te0tl.commons.platform.extension.android.setFragmentResultListener
import com.te0tl.commons.platform.extension.android.show
import com.te0tl.commons.presentation.fragment.BaseViewModelFragment
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.FragmentMoviesBinding
import com.te0tl.themoviesdb.domain.entity.Category
import com.te0tl.themoviesdb.platform.logging.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ARGUMENT_KEY_CATEGORY = "ARGUMENT_KEY_CATEGORY"
const val RESULT_QUERY_KEY = "RESULT_QUERY_KEY"
const val ARGUMENT_QUERY_KEY = "ARGUMENT_QUERY_KEY"

class MoviesFragment :
    BaseViewModelFragment<FragmentMoviesBinding, MoviesState, MoviesViewModel>() {

    override val viewModel: MoviesViewModel by viewModel()

    private lateinit var category: Category

    private lateinit var moviesAdapter: MoviesAdapter

    override fun getViewBinding(parent: ViewGroup) =
        FragmentMoviesBinding.inflate(layoutInflater, parent, false)

    override fun parseArguments() {
        checkNotNull(arguments) {
            "arguments can't be null"
        }
        arguments?.run {
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

        moviesAdapter = MoviesAdapter()
        viewBinding.recyclerView.adapter = moviesAdapter

        viewModel.getMovies(this.category)

    }

    override fun onNewViewModelState(newState: MoviesState) {
        with(viewBinding) {
            when (newState) {
                is MoviesState.MoviesReady -> {
                    contentContainer.show()
                    progressContainer.hide()
                    errorContainer.hide()
                    newState.movies.forEach {
                        moviesAdapter.addItem(it)
                    }
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

    private fun onFragmentResult(requestKey: String, result: Bundle) {
        check(RESULT_QUERY_KEY == requestKey)
        Logger.d("onQuerySubmit $category ${result.getString(ARGUMENT_QUERY_KEY)}")
    }

}