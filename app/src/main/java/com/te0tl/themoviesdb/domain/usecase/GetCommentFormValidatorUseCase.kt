package com.te0tl.themoviesdb.domain.usecase

import com.te0tl.common.domain.*

class GetCommentFormValidatorUseCase() : BaseUseCase<FormValidator<FormComments>, String>() {

    override suspend fun execute(): Res<FormValidator<FormComments>, String> = Res.Success(
        FormValidator<FormComments>(

            mapOf(
                FormComments.PUBLISHER to FormField(
                    FormComments.PUBLISHER, {
                        !it.isNullOrBlank() && it.trim().length >= 5
                    }
                ),
                FormComments.COMMENT to FormField(
                    FormComments.COMMENT
                ) {
                    !it.isNullOrBlank() && it.trim().split(" ").size >= 5
                }
            )
        )

    )

}

enum class FormComments {
    PUBLISHER, COMMENT
}