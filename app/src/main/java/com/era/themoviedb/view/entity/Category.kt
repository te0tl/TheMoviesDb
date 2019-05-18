package com.era.themoviedb.view.entity


sealed class Category {

    object Popular : Category() {
        override fun toString(): String {
            return "popular"
        }
    }

    object TopRated : Category() {
        override fun toString(): String {
            return "top_rated"
        }
    }

    object Upcoming : Category() {
        override fun toString(): String {
            return "upcoming"
        }
    }
}