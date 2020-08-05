package com.te0tl.commons.data.firebase

import android.util.Log
import kotlin.reflect.full.declaredMemberProperties

/**
 * Base class for entities mapped from Firebase-Firestore.
 *
 * YOU MUST ALWAYS PROVIDE default values for all DTO class fields so compiler could create
 * default constructor needed for firebaseDocument.toObject(DatabModel::class.java).
 */
interface FirebaseBaseEntity {

    /**
     * Utility method for convert any fields to a Map object.
     */
    fun toMap(): Map<String, Any> {
        return this.javaClass.kotlin.declaredMemberProperties.map {
            it.name to (it.get(this) ?: "")
        }.toMap()
    }

}