package com.te0tl.common.platform.extension.android

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


fun TextInputEditText.validate(errorLayout: TextInputLayout, validator: (String) -> Boolean, msgResId : Int, initValidation: Boolean = false) {
    this.afterTextChanged {
        errorLayout.error = if (validator(it)) null else this.context.getString(msgResId)
    }

    if (initValidation) errorLayout.error = if (validator(this.text.toString())) null else this.context.getString(msgResId)

}