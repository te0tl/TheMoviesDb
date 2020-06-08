package com.te0tl.commons.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.te0tl.commons.platform.extension.android.closeKeyboard
import com.te0tl.commons.platform.extension.android.showAlertDialog
import com.te0tl.commons.platform.extension.android.showToast
import com.te0tl.commons.presentation.BaseView
import com.te0tl.themoviesdb.R
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit

/**
 * Base activity to avoid boilerplate code for most of the activities.
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), BaseView {

    override val standardViewCreation = true
    protected open var showBackButton = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()

        if (standardViewCreation) {
            setContentView(idViewResource)
        }

        setupToolbar()
        setupFragmentManager()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        onViewAndExtrasReady()
    }
    protected fun getToolbar() : Toolbar {
        return toolbar
    }

    protected open fun setupToolbar() {
        toolbar?.apply {
            setSupportActionBar(toolbar)
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

    protected fun showDialog(message: String, title: String = getString(R.string.global_warning)) {
        showAlertDialog(title, message)
    }

    protected fun showDialogConfirm(message: String, title: String = getString(R.string.global_warning),
                                    listener: (confirmed: Boolean) -> Unit) {
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

    protected fun showShortToast(message: String) {
        showToast(message)
    }

    protected fun showLongToast(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    protected fun postDelayed(seconds : Int, block : () -> Unit) {
        Handler().postDelayed(Runnable(block), TimeUnit.SECONDS.toMillis(seconds.toLong()))
    }
}