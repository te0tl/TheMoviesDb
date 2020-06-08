package com.te0tl.commons.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.te0tl.commons.platform.extension.android.closeKeyboard
import com.te0tl.commons.platform.extension.android.showAlertDialog
import com.te0tl.commons.platform.extension.android.showToast
import com.te0tl.commons.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.R
import kotlinx.android.synthetic.main.toolbar.toolbar
import java.util.concurrent.TimeUnit

/**
 * Base activity to avoid boilerplate code for activities with ViewModel
 */
@SuppressLint("Registered")
abstract class BaseViewModelActivity<VMS, VM : BaseViewModel<VMS>> : BaseActivity() {

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.observeViewModelState(this, ::onNewViewModelState)
    }

    abstract fun onNewViewModelState(newState: VMS)

}