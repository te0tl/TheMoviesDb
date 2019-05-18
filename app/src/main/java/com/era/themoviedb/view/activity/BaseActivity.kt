package com.era.themoviedb.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.view.fragment.BaseFragment
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar.progressBar
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.bindScope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.util.concurrent.TimeUnit


@SuppressLint("Registered")
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView {

    protected val scope : Scope by lazy {
        getKoin().getOrCreateScope(scopeId, named(scopeId))
    }

    abstract val presenter : P

    /**
     * Give -1 if you don't want setContentView(getFragmentResourceId())
     */
    abstract val activityResourceId : Int

    protected open var showBackButton = true

    /**
     * If you're going to use koin scope, override this method
     */
    protected open var scopeId = "fake scope"


    private val onBackPressedListeners : MutableSet<OnBackPressedListener> by lazy {
        mutableSetOf<OnBackPressedListener>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupKoinScope()

        if (activityResourceId != -1) {
            setContentView(activityResourceId)
        }

        setupToolbar()

        setupFragmentManager()

        onPostCreateView()
    }

    open fun onPostCreateView() {}

    private fun setupFragmentManager() {
        supportFragmentManager?.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                onBackPressed()
            }
        }
    }

    private fun setupKoinScope() {
        if (scopeId != "fake scope") {
            bindScope(scope, Lifecycle.Event.ON_DESTROY)
        }
    }

    private fun setupToolbar() {
        toolbar?.apply {
            setSupportActionBar(toolbar)
        }

        if (showBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        presenter.onViewAvailable(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showErrorMessage(message: String) {
        longToast(message)
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun stopShowingProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun destroyView() {
        Logger.d("destroyView")
        Handler().post {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() {
        Logger.d("onBackPressed")
        supportFragmentManager?.apply {
            if (backStackEntryCount > 0) {
                val currentFragment = findFragmentByTag((backStackEntryCount - 1).toString()) as BaseFragment<*>
                currentFragment.onBackPressed()
            } else {
                presenter.onButtonBackPressed(this@BaseActivity)
            }
        }
    }

    override fun postDelayed(seconds : Int, block : () -> Unit) {

        Handler().postDelayed(Runnable(block), TimeUnit.SECONDS.toMillis(seconds.toLong()))
    }

    fun addOnBackPressedListener(onBackPressedListener: OnBackPressedListener) {
        onBackPressedListeners.add(onBackPressedListener)
    }

    interface OnBackPressedListener {
        fun onBackPressed()
    }
}