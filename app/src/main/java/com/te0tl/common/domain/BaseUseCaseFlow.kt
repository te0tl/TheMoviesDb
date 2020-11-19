package com.te0tl.common.domain

import kotlinx.coroutines.flow.Flow

/**
 * Base abstraction class for a use case:
 * D: Data object, the success result of
 * E: Error object, the failure result of
 * Output Error object, exceptions should be managed in da house, not to throw it.
 *
 * The process is executed in the Default Dispatcher.
 */
abstract class BaseUseCaseFlow<out D> {

    protected abstract fun execute(): Flow<ResDef<D>>

    operator fun invoke(): Flow<ResDef<D>> = execute()

}
