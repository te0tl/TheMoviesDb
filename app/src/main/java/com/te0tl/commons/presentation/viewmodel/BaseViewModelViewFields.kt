package com.te0tl.commons.presentation.viewmodel


abstract class BaseViewModelViewFields<VS> : BaseViewModel<VS>() {
    abstract val viewFields: BaseViewFields
}