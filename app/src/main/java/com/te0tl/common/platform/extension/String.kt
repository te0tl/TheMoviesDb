package com.te0tl.common.platform.extension

import kotlin.random.Random

fun randomString(): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val outputStrLength = (1..36).shuffled().first()

    return (1..outputStrLength)
            .map{ Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
}