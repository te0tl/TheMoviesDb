package com.te0tl.common.domain

/**
 * Default class error.
 *
 * Each layer should define their own code rules.
 */
data class ErrorDefault(val message: String, val code: Int = 0, val e: Exception? = null)
