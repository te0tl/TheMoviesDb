package com.te0tl.commons.platform.extension

val Throwable.safeMessage: String get() =
    if (message != null && !message.equals("")) message!! else "Unknown error"
