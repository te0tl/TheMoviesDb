package com.te0tl.commons.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.te0tl.commons.platform.extension.android.closeKeyboard
import com.te0tl.commons.platform.extension.android.showAlertDialog
import com.te0tl.commons.platform.extension.android.showToast
import com.te0tl.commons.presentation.BaseView
import com.te0tl.themoviesdb.R

/**
 * Base fragment to avoid boilerplate code for most of the fragments.
 */
abstract class BaseFragment : Fragment(), BaseView {

    override val standardViewCreation = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (standardViewCreation) {
            return inflater.inflate(idViewResource, container, false)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArguments()
        onViewAndExtrasReady()
    }

    protected fun showDialog(message : String, title : String = getString(R.string.global_warning)) {
        activity?.showAlertDialog(title, message)
    }

    protected fun showShortToast(message : String) {
        activity?.showToast(message)
    }

    protected fun showLongToast(message : String) {
        activity?.showToast(message, Toast.LENGTH_LONG)
    }

    protected fun showDialogConfirm(message: String, title: String = getString(R.string.global_warning),
                                    listener: (confirmed: Boolean) -> Unit) {
        activity?.closeKeyboard(activity?.currentFocus)

        AlertDialog.Builder(context!!)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.global_cancel)) { _, _ ->
                    listener(false)

                }
                .setPositiveButton(getString(R.string.global_yes)) { _, _ ->
                    listener(true)
                }
                .create().show()

    }

    protected fun finish() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

}
