package com.te0tl.commons.platform.extension.android

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar


fun CoordinatorLayout.showSnackbar(
    message: String,
    actionText: String,
    clickListener: () -> Unit,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, message, duration)
        .setAction(actionText, {
            clickListener()
        })
        .show()
}