package com.te0tl.themoviesdb.data.repository

import com.te0tl.common.data.firebase.FirestoreManager
import com.te0tl.common.domain.ErrorDefault
import com.te0tl.common.domain.ResDef
import com.te0tl.common.platform.extension.safeMessage
import com.te0tl.themoviesdb.data.firebase.dto.toDomain
import com.te0tl.themoviesdb.data.firebase.dto.toFirebaseDto
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.te0tl.themoviesdb.data.firebase.dto.Comment as CommentDto

private const val COMMENTS_COLLECTION = "comments"

@ExperimentalCoroutinesApi
class CommentsRepositoryImplV2(private val firestoreManager: FirestoreManager) :
    CommentsRepository {

    override suspend fun create(entity: Comment, vararg params: Any): ResDef<Comment> =
        withContext(Dispatchers.Default) {
            try {

                val inserted =
                    firestoreManager.insertDocument(COMMENTS_COLLECTION, entity.toFirebaseDto())

                ResDef.Success(inserted.toDomain())
            } catch (e: Exception) {
                ResDef.Failure(ErrorDefault(e.safeMessage))
            }

        }

    override suspend fun update(entity: Comment, vararg params: Any): ResDef<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: Comment, vararg params: Any): ResDef<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun get(vararg params: Any): ResDef<Comment?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(vararg params: Any): ResDef<List<Comment>> {
        TODO("Not yet implemented")
    }

    override fun flowAll(vararg params: Any): Flow<ResDef<Comment>> = flow {

        try {
            firestoreManager.getDocuments<CommentDto>(COMMENTS_COLLECTION).forEach {
                emit(ResDef.Success(it.toDomain()))
            }

        } catch (e: Exception) {
            emit(ResDef.Failure(ErrorDefault(e.safeMessage)))
        }

    }.flowOn(Dispatchers.Default)

}