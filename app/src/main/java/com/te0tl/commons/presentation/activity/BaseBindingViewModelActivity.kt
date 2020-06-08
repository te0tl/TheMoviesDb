package com.te0tl.commons.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.te0tl.commons.presentation.viewmodel.BaseViewModel

/**
 * Base activity to avoid boilerplate code for activities with ViewModel and View Binding
 */
@SuppressLint("Registered")
abstract class BaseBindingViewModelActivity<AB : ViewDataBinding, VMS, VM : BaseViewModel<VMS>> : BaseViewModelActivity<VMS, VM>() {

    private lateinit var binding: AB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, idViewResource)
        binding.lifecycleOwner = this

        setupBindings(binding)
        binding.executePendingBindings()
    }

    protected abstract fun setupBindings(binding: AB)

}