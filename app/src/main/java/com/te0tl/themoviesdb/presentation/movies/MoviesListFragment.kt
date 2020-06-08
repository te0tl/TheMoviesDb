package com.te0tl.themoviesdb.presentation.movies

import com.te0tl.commons.presentation.fragment.BaseViewModelFragment
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.domain.entities.Category
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.android.synthetic.main.fragment_list_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ARGUMENT_KEY_CATEGORY = "ARGUMENT_KEY_CATEGORY"

class MoviesListFragment: BaseViewModelFragment<MoviesListState, MoviesListViewModel>() {

    override val idViewResource = R.layout.fragment_list_movies

    override val viewModel: MoviesListViewModel by viewModel()

    private lateinit var category: Category

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
        fuck.text = category.name
    }

    override fun onNewViewModelState(newState: MoviesListState) {

    }

    fun onQuerySubmit(query : String) {
        Logger.d("onQuerySubmit $query")
    }

}