package com.te0tl.common.platform.extension

import android.os.Build
import java.util.*


@ExperimentalStdlibApi
val Any.deviceName: String
    get() {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer))
            model.capitalize(Locale.getDefault())
        else
            manufacturer.capitalize(Locale.getDefault()) + " " + model
    }