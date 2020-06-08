package com.te0tl.themoviesdb.presentation.splash

import com.te0tl.commons.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel<SplashState>() {

    init {
        uiScope.launch {
            //TODO SETUP SOME ENVIRONMENT SHITS
            updateViewModelState(SplashState.GoToMovies)
        }
    }

}

sealed class SplashState {
    object GoToMovies : SplashState()
}
