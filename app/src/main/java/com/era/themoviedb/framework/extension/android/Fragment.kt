package com.era.themoviedb.framework.extension.android

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * @author era on 11/04/19.

 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager?.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.addFragmentToBackStack(fragment: Fragment, frameId: Int,
                                             label : String = supportFragmentManager?.fragments?.size.toString()) {
    supportFragmentManager?.inTransaction {
        add(frameId, fragment, label).addToBackStack(label)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager?.inTransaction { replace(frameId, fragment) }
}
