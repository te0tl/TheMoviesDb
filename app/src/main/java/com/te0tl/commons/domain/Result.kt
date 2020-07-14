package com.te0tl.commons.domain

sealed class Result<out D, out E> {
    data class Success<out D>(val data: D): Result<D, Nothing>()
    data class Failure<out E>(val error: E): Result<Nothing, E>()
}

/**
 * If you want to get rid of when expressions:
 * result.ifFailure {error ->
 * }
 */
inline fun <reified  D, reified E> Result<D, E>.ifFailure(callback: (error: E) -> Unit) {
    if (this is Result.Failure) {
        callback(error)
    }
}

/**
 * If you want to get rid of when expressions:
 * result.ifSuccess {data ->
 * }
 */
inline fun <reified  D, reified E> Result<D, E>.ifSuccess(callback: (data: D) -> Unit) {
    if (this is Result.Success) {
        callback(data)
    }
}