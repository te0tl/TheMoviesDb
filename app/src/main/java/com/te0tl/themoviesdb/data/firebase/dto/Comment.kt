package com.te0tl.themoviesdb.data.firebase.dto

import com.google.firebase.Timestamp
import com.te0tl.commons.data.firebase.FirebaseBaseEntity
import java.util.*


class Comment(
    var publisher: String = "",
    var comment: String = "",
    var date: Timestamp? = null
) : FirebaseBaseEntity