package com.te0tl.commons.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.te0tl.commons.presentation.viewmodel.BaseViewModel

/**
 * Base fragment to avoid boilerplate code for fragments with ViewModel and View Binding
 */
abstract class BaseBindingViewModelFragment<AB : ViewDataBinding, VMS, VM : BaseViewModel<VMS>> :
    BaseViewModelFragment<VMS, VM>() {

    override val standardViewCreation = false

    private lateinit var binding: AB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, idViewResource, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings(binding)
        binding.executePendingBindings()
    }

    /**
     * Use this method for references into the view, there's not need to use
     * findViewById... just binding.myTextView...
     */
    protected open fun setupBindings(binding: AB) {}

}
