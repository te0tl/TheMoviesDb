package com.te0tl.common.domain

sealed class Res<out D, out E> {
    data class Success<out D>(val data: D): Res<D, Nothing>()
    data class Failure<out E>(val error: E): Res<Nothing, E>()
}

/**
 * Result default sealed class
 */
sealed class ResDef<out D> {
    data class Success<out D>(val data: D): ResDef<D>()
    data class Failure(val error: ErrorDefault): ResDef<Nothing>()
}


/**
 * If you want to get rid of when expressions:
 * result.ifFailure {error ->
 * }
 */
inline fun <reified  D, reified E> Res<D, E>.ifFailure(callback: (error: E) -> Unit) {
    if (this is Res.Failure) {
        callback(error)
    }
}

/**
 * If you want to get rid of when expressions:
 * result.ifSuccess {data ->
 * }
 */
inline fun <reified  D, reified E> Res<D, E>.ifSuccess(callback: (data: D) -> Unit) {
    if (this is Res.Success) {
        callback(data)
    }
}