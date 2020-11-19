package com.te0tl.common.domain.repository

import com.te0tl.common.domain.ResDef
import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {

    suspend fun create(entity: T, vararg params: Any) : ResDef<T>

    suspend fun update(entity: T, vararg params: Any) : ResDef<T>

    suspend fun delete(entity: T, vararg params: Any) : ResDef<Boolean>

    suspend fun get(vararg params: Any) : ResDef<T?>

    suspend fun getAll(vararg params: Any) : ResDef<List<T>>

    fun flowAll(vararg params: Any) : Flow<ResDef<T>>

}