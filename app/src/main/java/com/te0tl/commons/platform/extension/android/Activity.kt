package com.te0tl.commons.platform.extension.android

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import java.lang.Exception

fun Activity.requestFocus(view: View) {
    if (view.requestFocus()) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}

fun Activity.closeKeyboard(view: View?) {
    try {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: Exception) { }
}

fun Activity.openKeyboard(view: View) {
    requestFocus(view)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun AppCompatActivity.isPermissionGranted(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun FragmentActivity.showAlertDialog(title: String?, message: String?) {
    AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton("OK", null)
            .create().show()
}

fun FragmentActivity.showToast(message: String?, duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun FragmentActivity.showToast(messageResId: Int, duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(messageResId), duration).show()
}