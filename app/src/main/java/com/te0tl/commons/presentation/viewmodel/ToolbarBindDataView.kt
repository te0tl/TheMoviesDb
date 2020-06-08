package com.te0tl.commons.presentation.viewmodel

import androidx.databinding.ObservableField

data class ToolbarBindDataView(val title: ObservableField<String>,
                               val showProgress: ObservableField<Boolean> = ObservableField(false))