package com.te0tl.common.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.te0tl.common.platform.extension.android.closeKeyboard
import com.te0tl.common.platform.extension.android.showAlertDialog
import com.te0tl.common.platform.extension.android.showToast
import com.te0tl.common.presentation.BaseView
import com.te0tl.themoviesdb.R

/**
 * Base activity to avoid boilerplate code for most of the activities.
 */
@SuppressLint("Registered")
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), BaseView<VB> {
    override lateinit var stringProvider: Context

    protected open val standardViewBinding = true
    protected open var showBackButton = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stringProvider = this;

        parseArguments(intent?.extras)

        if (standardViewBinding) {
            setContentView(viewBinding.root)
            setupToolbar()
        }

        setupFragmentManager()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onViewAndExtrasReady()
    }

    protected open fun setupToolbar() {
        viewBinding.root.findViewById<Toolbar>(R.id.toolbar)?.apply {
            setSupportActionBar(this)
        }

        if (showBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupFragmentManager() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                onBackPressed()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showDialog(message: String, title: String) {
        showAlertDialog(title, message)
    }

    override fun showShortToast(message: String) {
        showToast(message)
    }

    override fun showLongToast(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    override fun showDialogConfirm(
        message: String, title: String,
        listener: (confirmed: Boolean) -> Unit
    ) {
        closeKeyboard(currentFocus)

        AlertDialog.Builder(this)
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


}