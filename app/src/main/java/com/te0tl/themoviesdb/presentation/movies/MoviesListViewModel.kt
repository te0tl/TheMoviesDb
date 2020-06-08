package com.te0tl.themoviesdb.presentation.movies

import com.te0tl.commons.presentation.viewmodel.BaseViewFields
import com.te0tl.commons.presentation.viewmodel.BaseViewModelViewFields

class MoviesListViewModel : BaseViewModelViewFields<MoviesListState>() {

    override val viewFields = MoviesListViewFields()


}

class MoviesListViewFields : BaseViewFields() {
}

sealed class MoviesListState {

}
