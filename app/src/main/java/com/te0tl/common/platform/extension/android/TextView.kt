package com.te0tl.common.platform.extension.android

import android.widget.TextView

val TextView.textString : String get() = this.text.toString()
