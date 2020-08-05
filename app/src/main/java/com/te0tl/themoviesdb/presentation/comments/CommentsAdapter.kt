package com.te0tl.themoviesdb.presentation.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.te0tl.commons.platform.extension.android.inflate
import com.te0tl.commons.platform.extension.android.loadFromUrl
import com.te0tl.commons.platform.extension.formatToViewDateTimeDefaults
import com.te0tl.commons.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.RecyclerviewCommentItemBinding
import com.te0tl.themoviesdb.databinding.RecyclerviewMovieItemBinding
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.entity.Movie
import com.te0tl.themoviesdb.platform.logging.Logger

class CommentsAdapter : BaseRecyclerViewAdapter<RecyclerviewCommentItemBinding, Comment>(false) {

    override fun instantiateViewBinding(parent: ViewGroup) =
        RecyclerviewCommentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun bindData(viewBinding: RecyclerviewCommentItemBinding, model: Comment, position: Int) {
        with(viewBinding) {
            txtViewNickname.text = model.publisher
            txtViewComment.text = model.comment
            txtViewDate.text = model.date?.formatToViewDateTimeDefaults()
        }
    }

    override fun equals(model: Comment, otherModel: Comment) = model.equals(otherModel)

}