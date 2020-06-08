package com.te0tl.commons.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.te0tl.commons.presentation.viewmodel.BaseViewModel

/**
 * Base fragment to avoid boilerplate code for fragments with ViewModel
 */
abstract class BaseViewModelFragment<VMS, VM : BaseViewModel<VMS>> : BaseFragment() {

    protected abstract val viewModel: VM

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeViewModelState(this, ::onNewViewModelState)
    }

    abstract fun onNewViewModelState(newState : VMS)

}
