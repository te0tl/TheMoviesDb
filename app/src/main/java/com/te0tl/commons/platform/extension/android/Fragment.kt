package com.te0tl.commons.platform.extension.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, tag: String? = null) {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag) }
}

fun AppCompatActivity.addHiddenFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment).hide(fragment) }
}

fun AppCompatActivity.toggleFragment(
    idContainer: Int,
    tagFragmentToShow: String,
    fragmentToHide: Fragment,
    fragmentToShow: Fragment
) {

    if (supportFragmentManager.findFragmentByTag(tagFragmentToShow) == null) {
        addFragment(fragmentToShow, idContainer, tagFragmentToShow)
    }

    supportFragmentManager.inTransaction {
        hide(fragmentToHide)
        show(fragmentToShow)
    }
}

fun AppCompatActivity.addFragmentToBackStack(
    fragment: Fragment, frameId: Int,
    label: String = supportFragmentManager.fragments.size.toString()
) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment, label).addToBackStack(label)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Fragment.setFragmentResultListener(
    key: String,
    resultListener: (result: Bundle) -> Unit
) {
    parentFragmentManager.setFragmentResultListener(
        key,
        this,
        FragmentResultListener { _, result ->
            resultListener(result)
        }
    )
}

fun Fragment.sendFragmentResult(key: String, bundle: Bundle) {
    parentFragmentManager.setFragmentResult(
        key, bundle
    )
}