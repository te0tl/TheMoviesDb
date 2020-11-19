package com.te0tl.common.data.firebase

import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirestoreManager {

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()

        Firebase.firestore.firestoreSettings = settings
    }

    suspend inline fun <reified T : FirebaseEntity> getDocuments(pathToCollection: String): List<T> =
        withContext(Dispatchers.IO) {

            val result = Firebase.firestore.collection(pathToCollection)
                .get()
                .await()

            val documents = mutableListOf<T>()
            for (document in result) {
                documents.add(document.toObject())
            }
            documents

        }

    suspend inline fun <reified T : FirebaseEntity> getDocument(pathToDocument: String): T? =
        withContext(Dispatchers.IO) {

            val result = Firebase.firestore.document(pathToDocument)
                .get()
                .await()

            result.toObject()

        }

    suspend inline fun <reified T : FirebaseEntity> insertDocument(
        pathToCollection: String,
        entity: T
    ): T =
        withContext(Dispatchers.IO) {

            val result = Firebase.firestore.collection(pathToCollection).add(entity.toMap())
                .await()

            entity.apply { idFirebase = result.id }

            entity

        }

    suspend inline fun <reified T : FirebaseEntity> updateDocument(
        pathToCollection: String,
        entity: T
    ): T =
        withContext(Dispatchers.IO) {

            Firebase.firestore.collection(pathToCollection).document(entity.idFirebase!!)
                .set(entity.toMap(), SetOptions.merge())
                .await()

            entity

        }

    suspend inline fun <reified T : FirebaseEntity> deleteDocument(
        pathToCollection: String,
        entity: T
    ): Boolean =
        withContext(Dispatchers.IO) {

            Firebase.firestore.collection(pathToCollection).document(entity.idFirebase!!)
                .delete()
                .await()

            true

        }
}