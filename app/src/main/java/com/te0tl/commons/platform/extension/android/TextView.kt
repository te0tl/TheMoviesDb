package com.te0tl.commons.platform.extension.android

import android.widget.TextView

val TextView.textString : String get() = this.text.toString()
