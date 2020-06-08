package com.te0tl.commons.platform.extension.android

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

fun ImageView.loadFromUrl(imageUrl: String) {
    Glide.with(this)
            .load(imageUrl)
            .into(this)
}


fun ImageView.loadFromFile(file: File) {
    Glide.with(this)
            .load(file)
            .fitCenter()
            .into(this)
}