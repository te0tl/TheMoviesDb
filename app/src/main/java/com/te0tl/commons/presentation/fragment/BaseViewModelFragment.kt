package com.te0tl.commons.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.te0tl.commons.presentation.viewmodel.BaseViewModel

/**
 * Base fragment to avoid boilerplate code for fragments with ViewModel
 */
abstract class BaseViewModelFragment<VB: ViewBinding, VMS, VM : BaseViewModel<VMS>> : BaseFragment<VB>() {

    protected abstract val viewModel: VM

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeViewModelState(this, ::onNewViewModelState)
    }

    abstract fun onNewViewModelState(newState : VMS)

}
