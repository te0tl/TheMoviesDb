package com.era.themoviedb.framework.extension.android

import android.widget.TextView

/**
 * @author era on 08/04/19.

 */
val TextView.textString : String get() = this.text.toString()
