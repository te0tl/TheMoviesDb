package com.era.themoviedb.framework.extension.android

import android.view.View

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.setVisible() { this.visibility = View.VISIBLE }

fun View.setInvisible() { this.visibility = View.INVISIBLE }

fun View.setGone() { this.visibility = View.GONE }
