package com.te0tl.common.presentation.view

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.Delegates

/**
 * Default DiffCallback for data classes
 */
class DefaultItemDiffCallback<M> : DiffUtil.ItemCallback<M>() {
    override fun areItemsTheSame(oldItem: M, newItem: M) = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: M, newItem: M) = oldItem == oldItem
}

/**
 *
 * Base class to avoid a lot of boiler plate code for common Recyclers.
 * VB: View Binding Instance.
 * M: Model to bind data with the View Holder.
 */
abstract class BaseRecyclerViewAdapterV2<VB : ViewBinding, M>(diffCallback: DiffUtil.ItemCallback<M> = DefaultItemDiffCallback()) :
    ListAdapter<M, BaseRecyclerViewAdapterV2<VB, M>.ViewHolder<M>>(diffCallback) {

    private var itemClickListener: ItemClickListener<M>? = null

    abstract fun instantiateViewBinding(parent: ViewGroup): VB

    abstract fun bindData(viewBinding: VB, model: M, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder<M> {
        return ViewHolder(instantiateViewBinding(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder<M>, position: Int) {
        bindData(holder.viewBinding, getItem(position), position)
    }

    fun interface ItemClickListener<M> {
        fun onItemClick(item: M)
    }

    fun setOnItemClickListener(itemClickListener: ItemClickListener<M>) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder<M>(val viewBinding: VB) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(getItem(layoutPosition))
            }
        }
    }

}