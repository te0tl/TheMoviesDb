package com.te0tl.common.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.te0tl.common.presentation.viewmodel.BaseViewModel

/**
 * Base activity to avoid boilerplate code for activities with ViewModel
 */
@SuppressLint("Registered")
abstract class BaseViewModelActivity<VB: ViewBinding, VMS, VM : BaseViewModel<VMS>> : BaseActivity<VB>() {

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observeViewModelState(this, ::onNewViewModelState)
    }

    abstract fun onNewViewModelState(newState: VMS)

}