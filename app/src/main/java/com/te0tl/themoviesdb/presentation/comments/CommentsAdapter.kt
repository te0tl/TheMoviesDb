package com.te0tl.themoviesdb.presentation.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.te0tl.common.platform.extension.formatToViewDateTimeDefaults
import com.te0tl.common.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.common.presentation.view.BaseRecyclerViewAdapterV2
import com.te0tl.themoviesdb.databinding.RecyclerviewCommentItemBinding
import com.te0tl.themoviesdb.domain.entity.Comment

class CommentsAdapter : BaseRecyclerViewAdapterV2<RecyclerviewCommentItemBinding, Comment>() {

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
            txtViewDate.text = model.date.formatToViewDateTimeDefaults()
        }
    }
}