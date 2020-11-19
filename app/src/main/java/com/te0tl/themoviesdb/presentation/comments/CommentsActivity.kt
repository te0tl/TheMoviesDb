package com.te0tl.themoviesdb.presentation.comments

import androidx.appcompat.app.AlertDialog
import com.te0tl.common.domain.FormValidator
import com.te0tl.common.platform.extension.android.*
import com.te0tl.common.platform.extension.formatToViewDateTimeDefaults
import com.te0tl.common.presentation.activity.BaseViewModelActivity
import com.te0tl.common.presentation.view.BaseRecyclerViewAdapter
import com.te0tl.common.presentation.view.BaseRecyclerViewAdapterV2
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.ActivityCommentsBinding
import com.te0tl.themoviesdb.databinding.DialogAddCommentBinding
import com.te0tl.themoviesdb.databinding.DialogCommentBinding
import com.te0tl.themoviesdb.domain.entity.Comment
import com.te0tl.themoviesdb.domain.usecase.FormComments
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsActivity :
    BaseViewModelActivity<ActivityCommentsBinding, CommentsState, CommentsViewModel>(),
    BaseRecyclerViewAdapterV2.ItemClickListener<Comment> {

    override val viewBinding: ActivityCommentsBinding by lazy {
        ActivityCommentsBinding.inflate(layoutInflater)
    }

    override val viewModel: CommentsViewModel by viewModel()

    private lateinit var commentsAdapter: CommentsAdapter

    private lateinit var formValidator: FormValidator<FormComments>

    override fun onViewAndExtrasReady() {

        with(viewBinding) {
            fab.setOnClickListener {
                showDialogAddComment()
            }
        }

        commentsAdapter = CommentsAdapter()
        commentsAdapter.setOnItemClickListener(this)

        viewBinding.recyclerView.adapter = commentsAdapter

        viewModel.getComments()
        viewModel.getCommentFormValidator()

    }

    override fun onNewViewModelState(newState: CommentsState) {
        with(viewBinding) {
            when (newState) {
                is CommentsState.CommentsReady -> {
                    includedToolbar.errorBar.hide()
                    includedToolbar.errorBar.text = ""
                    commentsAdapter.submitList(newState.comments)
                    recyclerView.scrollToPosition(newState.comments.size - 1);

                }

                is CommentsState.CommentFormValidatorReady -> {
                    this@CommentsActivity.formValidator = newState.formValidator
                    fab.show()
                }

                is CommentsState.Error -> {
                    includedToolbar.errorBar.show()
                    includedToolbar.errorBar.text = newState.error
                }
            }
        }

    }

    override fun onItemClick(item: Comment) {
        val dialogBuilder = AlertDialog.Builder(this)

        val dialogViewBinding = DialogCommentBinding.inflate(layoutInflater)

        dialogBuilder.setView(dialogViewBinding.root)
            .setCancelable(true)
            .setNeutralButton(getString(R.string.global_ok), null)

        val dialog = dialogBuilder.create()

        dialog.setOnShowListener {

            with(dialogViewBinding) {
                txtViewPublisher.text = item.publisher
                txtViewComment.text = item.comment
                txtViewDate.text = item.date?.formatToViewDateTimeDefaults()

            }

        }

        dialog.show()
    }

    private fun showDialogAddComment() {

        val dialogBuilder = AlertDialog.Builder(this)

        val dialogViewBinding = DialogAddCommentBinding.inflate(layoutInflater)

        dialogBuilder.setView(dialogViewBinding.root)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.global_cancel), null)
            .setPositiveButton(
                getString(R.string.send), { _, _ ->
                    viewModel.addComment(
                        dialogViewBinding.textInputPubliser.textString,
                        dialogViewBinding.textInputComment.textString
                    )
                }
            )

        val dialog = dialogBuilder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.isEnabled = false

            with(dialogViewBinding) {
                textInputPubliser.validate(layoutPublisher, {

                    button.isEnabled = formValidator.validateForm(
                        mapOf(
                            FormComments.PUBLISHER to it,
                            FormComments.COMMENT to textInputComment.textString
                        )
                    )

                    formValidator.validateField(FormComments.PUBLISHER, it)
                }, R.string.publisher_not_valid)

                textInputComment.validate(dialogViewBinding.layoutComment, {
                    button.isEnabled = formValidator.validateForm(
                        mapOf(
                            FormComments.PUBLISHER to it,
                            FormComments.COMMENT to textInputComment.textString
                        )
                    )

                    formValidator.validateField(FormComments.COMMENT, it)
                }, R.string.comment_not_valid)
            }

        }

        dialog.show()
    }

}