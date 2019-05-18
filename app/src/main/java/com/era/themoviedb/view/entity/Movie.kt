package com.era.themoviedb.view.entity

data class Movie(val id: Int, val title : String, val overview : String,
                 val posterUrl : String, val date : String?, val category: Category) {

    override fun toString(): String {
        return title + date.toString()
    }
}