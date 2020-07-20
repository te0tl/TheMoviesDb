package com.te0tl.commons.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.te0tl.commons.platform.extension.android.closeKeyboard
import com.te0tl.commons.platform.extension.android.showAlertDialog
import com.te0tl.commons.platform.extension.android.showToast
import com.te0tl.themoviesdb.R
import java.util.concurrent.TimeUnit

/**
 * Base class for common methods and attributes
 * to avoid boilerplate code for base activities and fragments.
 */
interface BaseView<VB : ViewBinding> {
    abstract val stringProvider: Context

    abstract val viewBinding: VB

    /**
     * Method for checking arguments/extras,
     * throw exceptions if required and not present
     */
    fun parseArguments(bundle: Bundle?) {}

    /**
     * At this point, view has been created and arguments/extras params parsed
     * then you can implement all view logic
     */
    fun onViewAndExtrasReady() {}

    /**
     * Common function always used in Activity/Fragment
     */
    fun postDelayed(seconds: Int, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), TimeUnit.SECONDS.toMillis(seconds.toLong()))
    }

    fun showDialog(message: String, title: String = stringProvider.getString(R.string.global_warning))

    fun showShortToast(message: String)

    fun showLongToast(message: String)

    fun showDialogConfirm(
        message: String, title: String = stringProvider.getString(R.string.global_warning),
        listener: (confirmed: Boolean) -> Unit
    )
}