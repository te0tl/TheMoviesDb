package com.te0tl.commons.presentation.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration

class RecyclerViewLinear : RecyclerView {
    private val dividerItemDecoration: DividerItemDecoration

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {

        setHasFixedSize(true)
        val manager = LinearLayoutManager(context)
        layoutManager = manager
        (layoutManager as LinearLayoutManager).isAutoMeasureEnabled = true
        itemAnimator = DefaultItemAnimator()


        dividerItemDecoration = DividerItemDecoration(context, manager.orientation)
        addItemDecoration(dividerItemDecoration)
    }

    fun removeDivider() {
        removeItemDecoration(dividerItemDecoration)
    }

    fun setupScrollListener(onLastItem: () -> Unit) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                        as LinearLayoutManager

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    onLastItem()
                }
            }
        })
    }

}