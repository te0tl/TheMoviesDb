package com.te0tl.commons.platform.extension.android

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.hasNetworkConnection() = networkInfo?.isConnected?: false

