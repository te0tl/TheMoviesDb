package com.te0tl.commons.domain

/**
 * Classes for holding business rules for inputs.
 *
 * Regex and error message, these can be validated in the View
 * but created and known only in the Usecase.
 *
 */

class FormValidator<KEY>(private val fields: Map<KEY, FormField<KEY>>) {

    fun validateForm(fieldsValues: Map<KEY, String>): Boolean {

        for ((key, value) in fieldsValues) {

            if (!fields[key]!!.validate(value)) {
                return false
            }

        }

        return true
    }

    fun validateField(keyField: KEY, value: String) = fields[keyField]!!.validate(value)

}

data class FormField<KEY> (
    val keyField: KEY,
    val validate: (String) -> Boolean
)

