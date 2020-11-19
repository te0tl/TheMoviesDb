package com.te0tl.common.data.firebase

import kotlin.reflect.full.declaredMemberProperties

/**
 * Base class for entities mapped from Firebase-Firestore.
 *
 * YOU MUST ALWAYS PROVIDE default values for all DTO class fields so compiler could create
 * default constructor needed for firebaseDocument.toObject(DataModel::class.java).
 *
 */
interface FirebaseEntity {

    var idFirebase: String?

    /**
     * Utility method for convert any fields to a Map object.
     */
    fun toMap(): Map<String, Any> {
        return this.javaClass.kotlin.declaredMemberProperties.filter{it.name != "idFirebase"}.map {
            it.name to (it.get(this) ?: "")
        }.toMap()
    }


}