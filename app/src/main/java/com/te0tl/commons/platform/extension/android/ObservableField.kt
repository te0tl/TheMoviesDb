package com.te0tl.commons.platform.extension.android

import androidx.databinding.ObservableField

var ObservableField<String>.value: String
    get() = get() ?: ""
    set(value) { set(value) }

var ObservableField<Boolean>.value: Boolean
    get() = get() ?: false
    set(value) { set(value) }

