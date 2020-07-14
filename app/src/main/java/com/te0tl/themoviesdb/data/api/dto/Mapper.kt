package com.te0tl.themoviesdb.data.api.dto

import com.te0tl.themoviesdb.domain.entity.Category
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Mapper {

    fun toCategoryDto(category: Category) = category.toString().toLowerCase(Locale.getDefault())

    fun toDateModel(date: String) : String {
        return try {
            SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
                .format(date)
        } catch (e: Exception) {
             ""
        }
    }

}