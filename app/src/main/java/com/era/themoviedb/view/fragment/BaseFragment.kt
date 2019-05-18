package com.era.themoviedb.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.era.themoviedb.contract.common.BasePresenter
import com.era.themoviedb.contract.common.BaseView
import com.era.themoviedb.view.activity.BaseActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.longToast
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


abstract class BaseFragment<P : BasePresenter> : Fragment(), BaseView, BaseActivity.OnBackPressedListener {

    abstract val presenter : P

    abstract val fragmentResourceId : Int

    /**
     * If you're going to use koin scope, override this method
     */
    protected open var scopeId = "fake scope"

    protected val scope : Scope by lazy {
        getKoin().getOrCreateScope(scopeId, named(scopeId))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(fragmentResourceId, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onPostCreateView(view)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(activity as BaseActivity<*>) {
            addOnBackPressedListener(this@BaseFragment)
        }
    }

    open fun onPostCreateView(rootView : View) {}

    override fun onStart() {
        super.onStart()
        presenter.onViewAvailable(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed() {
        Logger.d("onBackPressed")
        presenter.onButtonBackPressed(this)
    }

    override fun showMessage(message: String) {
        with(activity as BaseActivity<*>) {
            showProgress()
        }
    }

    override fun showErrorMessage(message: String) {
        with(activity as BaseActivity<*>) {
            longToast(message)
        }
    }

    override fun showProgress() {
        with(activity as BaseActivity<*>) {
            progressBar?.visibility = View.VISIBLE
        }
    }

    override fun stopShowingProgress() {
        with(activity as BaseActivity<*>) {
            progressBar?.visibility = View.GONE
        }
    }

    override fun destroyView() {
        Logger.d("destroyView")
        with(activity as BaseActivity<*>) {
            supportFragmentManager?.popBackStack()
        }
    }

    override fun postDelayed(seconds: Int, block: () -> Unit) {
        with(activity as BaseActivity<*>) {
            postDelayed(seconds, block)
        }
    }

}