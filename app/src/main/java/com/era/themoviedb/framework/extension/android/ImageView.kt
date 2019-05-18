package com.era.themoviedb.framework.extension.android

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.era.themoviedb.R

fun ImageView.loadFromUrl(url: String) {
    Glide.with(this.context.applicationContext)
        .load(url)
        .error(R.drawable.image_placeholder)
        .placeholder(R.drawable.image_placeholder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}