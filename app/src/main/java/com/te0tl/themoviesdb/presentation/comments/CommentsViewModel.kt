package com.te0tl.themoviesdb.presentation.comments

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.te0tl.common.domain.FormValidator
import com.te0tl.common.domain.Res
import com.te0tl.common.domain.ResDef
import com.te0tl.common.presentation.viewmodel.BaseViewModel
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.usecase.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val context: Context,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val getCommentFormValidatorUseCase: GetCommentFormValidatorUseCase
) :
    BaseViewModel<CommentsState>() {

    /**
     * If we are using flow datasource we may loose data 'cause LiveData is single state and
     * his buffer has only one state so we need to store locally in the viewmodel.
     */
    private val comments = mutableListOf<Comment>()

    fun getComments() {
        viewModelScope.launch {
            getCommentsUseCase().collect {
                when (it) {
                    is ResDef.Success -> {
                        comments.add(it.data)
                        updateViewModelState(CommentsState.CommentsReady(comments))
                    }
                    is ResDef.Failure -> {
                        updateViewModelState(CommentsState.Error(it.error.message))
                    }
                }
            }
        }
    }

    fun getCommentFormValidator() {
        viewModelScope.launch {
            when (val result = getCommentFormValidatorUseCase()) {
                is Res.Success -> {
                    updateViewModelState(CommentsState.CommentFormValidatorReady(result.data))
                }
                is Res.Failure -> {
                    updateViewModelState(CommentsState.Error(result.error))
                }
            }
        }
    }

    fun addComment(publisher: String, comment: String) {
        viewModelScope.launch {
            when (val result =
                addCommentUseCase(Comment(publisher = publisher, comment = comment))) {
                is Res.Failure -> {
                    updateViewModelState(CommentsState.Error(result.error))
                }
            }
        }
    }

}

sealed class CommentsState {
    data class CommentsReady(val comments: List<Comment>) : CommentsState()
    data class CommentFormValidatorReady(val formValidator: FormValidator<FormComments>) :
        CommentsState()

    data class Error(val error: String) : CommentsState()
}
