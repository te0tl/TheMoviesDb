package com.te0tl.commons.platform.extension.android

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

fun EditText.addRightDrawable(idDrawable : Int) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, context.getDrawable(idDrawable), null)
}

fun EditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun EditText.validate(validator: (String) -> Boolean, msgResId : Int) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else this.context.getString(msgResId)
    }
    this.error = if (validator(this.text.toString())) null else this.context.getString(msgResId)
}