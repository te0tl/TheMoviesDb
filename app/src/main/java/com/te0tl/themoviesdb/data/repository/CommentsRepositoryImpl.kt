package com.te0tl.themoviesdb.data.repository

private const val COMMENTS_COLLECTION = "comments"

//@ExperimentalCoroutinesApi
//class CommentsRepositoryImpl : CommentsRepository {
//
//    private val firestore: FirebaseFirestore by lazy {
//        Firebase.firestore
//    }
//
//    private val commentsCollection: CollectionReference by lazy {
//        firestore.collection(COMMENTS_COLLECTION)
//    }
//
//    override fun getComments(): Flow<Res<Comment, String>> = callbackFlow {
//        val subscription = commentsCollection
//            .orderBy("date")
//            .addSnapshotListener { snapshots, e ->
//                if (e != null) {
//                    close(e)
//                    Logger.e(e)
//                    offer(Res.Failure(e.safeMessage))
//                    return@addSnapshotListener
//                }
//
//                snapshots?.also {
//                    snapshots.documentChanges.forEach {
//                        it?.apply {
//                            when (type) {
//                                DocumentChange.Type.ADDED -> {
////                                Logger.d("ADDED ${document.data}")
//                                    val comment = Mapper.dtoCommentToModel(document)
//                                    offer(Res.Success(comment))
//
//                                }
//                                DocumentChange.Type.MODIFIED -> {
////                                Logger.d("Wont happen MODIFIED: ${document.data}")
//                                }
//                                DocumentChange.Type.REMOVED -> {
////                                Logger.d("Wont happen REMOVED: ${document.data}")
//                                }
//                            }
//                        }
//                    }
//                }
//
//            }
//
//        awaitClose { subscription.remove() }
//
//    }.flowOn(Dispatchers.IO)
//
//    override suspend fun publishComment(comment: Comment) {
//        withContext(Dispatchers.IO) {
//            try {
//                commentsCollection.document()
//                    .set(comment.toFirebaseDto().toMap())
//            } catch (e: Exception) {
//                Logger.e(e)
//            }
//        }
//    }
//
//}