package com.te0tl.themoviesdb.presentation

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import com.te0tl.common.presentation.activity.BaseActivity
import com.te0tl.themoviesdb.R
import com.te0tl.themoviesdb.databinding.ActivityAboutUsBinding


class AboutUsActivity : BaseActivity<ActivityAboutUsBinding>() {
    override val viewBinding: ActivityAboutUsBinding by lazy {
        ActivityAboutUsBinding.inflate(layoutInflater)
    }

    override fun onViewAndExtrasReady() {
        viewBinding.textView.text = Html.fromHtml(getString(R.string.about_us), FROM_HTML_MODE_LEGACY)
    }

}