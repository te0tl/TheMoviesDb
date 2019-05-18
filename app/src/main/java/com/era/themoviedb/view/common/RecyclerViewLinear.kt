package com.era.themoviedb.view.common

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator

class RecyclerViewLinear : RecyclerView {

    constructor(context: Context) : super (context)
    constructor(context: Context, attrs: AttributeSet?) : super (context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super (context, attrs, defStyle)

    init {

        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        (layoutManager as LinearLayoutManager).isAutoMeasureEnabled = true
        itemAnimator = DefaultItemAnimator()

    }

    fun setupScrollListener(onLastItem : () -> Unit) {
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