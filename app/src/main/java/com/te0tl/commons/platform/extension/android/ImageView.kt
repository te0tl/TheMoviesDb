package com.te0tl.commons.platform.extension.android

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.io.File

fun ImageView.loadFromUrl(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}


fun ImageView.loadFromFile(file: File) {
    Glide.with(this)
        .load(file)
        .transition(DrawableTransitionOptions.withCrossFade())
        .fitCenter()
        .into(this)
}