package com.era.themoviedb.contract

import com.era.themoviedb.contract.common.BaseModel
import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView

interface MvpSplash {

    interface Model : BaseModel {

    }

    interface View : BaseView {
        fun goToMovies()
    }

    interface Presenter : BasePresenter {

    }
}