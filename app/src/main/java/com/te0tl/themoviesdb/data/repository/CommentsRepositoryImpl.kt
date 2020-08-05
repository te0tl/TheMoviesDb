package com.te0tl.themoviesdb.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.te0tl.commons.domain.Result
import com.te0tl.commons.platform.extension.android.hasNetworkConnection
import com.te0tl.commons.platform.extension.safeMessage
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.data.api.BASE_URL_IMAGES
import com.te0tl.themoviesdb.data.firebase.dto.Mapper
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.entity.MovieDetail
import com.te0tl.themoviesdb.domain.entity.YoutubeVideo
import com.te0tl.themoviesdb.domain.repository.CommentsRepository
import com.te0tl.themoviesdb.platform.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.Exception

private const val COMMENTS_COLLECTION = "comments"

@ExperimentalCoroutinesApi
class CommentsRepositoryImpl : CommentsRepository {

    private val firestore: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val commentsCollection: CollectionReference by lazy {
        firestore.collection(COMMENTS_COLLECTION)
    }

    override fun getComments(): Flow<Result<Comment, String>> = callbackFlow {
        val subscription = commentsCollection
            .orderBy("date")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    close(e)
                    Logger.e(e)
                    offer(Result.Failure(e.safeMessage))
                    return@addSnapshotListener
                }

                snapshots?.also {
                    snapshots.documentChanges.forEach {
                        it?.apply {
                            when (type) {
                                DocumentChange.Type.ADDED -> {
//                                Logger.d("ADDED ${document.data}")
                                    val comment = Mapper.dtoCommentToModel(document)
                                    offer(Result.Success(comment))

                                }
                                DocumentChange.Type.MODIFIED -> {
//                                Logger.d("Wont happen MODIFIED: ${document.data}")
                                }
                                DocumentChange.Type.REMOVED -> {
//                                Logger.d("Wont happen REMOVED: ${document.data}")
                                }
                            }
                        }
                    }
                }

            }

        awaitClose { subscription.remove() }

    }.flowOn(Dispatchers.IO)

    override suspend fun publishComment(comment: Comment) {
        withContext(Dispatchers.IO) {
            try {
                commentsCollection.document()
                    .set(Mapper.modelToDto(comment).toMap(), SetOptions.merge())
            } catch (e: Exception) {
                Logger.e(e)
            }
        }
    }

}