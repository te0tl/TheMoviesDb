package com.te0tl.common.presentation.view

import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.Delegates

/**
 *
 * Base class to avoid a lot of boiler plate code for common Recyclers.
 * VB: View Binding Instance.
 * M: Model to bind data with the View Holder.
 */
abstract class BaseRecyclerViewAdapter<VB : ViewBinding, M>(private val searchable: Boolean = false) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<VB, M>.ViewHolder<M>>() {

    var itemClickListener: ItemClickListener<M>? = null

    var items: List<M> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList)
    }

    private var itemsOriginals = emptyList<M>()
    private var itemsFiltered = emptyList<M>()

    private var searchFilter: Filter? = null
    private var lastQuery = ""

    init {
        if (searchable) {
            itemsFiltered = items.toMutableList()
            itemsOriginals = items.toMutableList()
            setupSearch()
        }
    }

    abstract fun instantiateViewBinding(parent: ViewGroup): VB

    abstract fun bindData(viewBinding: VB, model: M, position: Int)

    abstract fun equals(model: M, otherModel: M): Boolean

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder<M> {
        return ViewHolder(instantiateViewBinding(parent))
    }

    override fun onBindViewHolder(holder: ViewHolder<M>, position: Int) {
        bindData(holder.viewBinding, items[position], position)
    }

    fun clearItems() {
        this.items = emptyList()

        if (searchable) {
            itemsFiltered = items.toMutableList()
            itemsOriginals = items.toMutableList()
            performSearch(lastQuery)
        }
    }

    fun replaceItems(items: List<M>) {
        if (searchable) {
            this.itemsOriginals = items.toMutableList()
            performSearch(lastQuery)
        } else {
            this.items = items.toMutableList()
        }
    }

    fun addItems(items: List<M>) {
        val newList = this.items.toMutableList()
        newList.addAll(items.toMutableList())

        if (searchable) {
            this.itemsOriginals = newList
            performSearch(lastQuery)
        } else {
            this.items = newList.toMutableList()
        }
    }

    fun addItem(item: M) {
        if (searchable) {
            this.itemsOriginals = this.itemsOriginals.toMutableList().apply { add(item) }
            performSearch(lastQuery)
        } else {
            this.items = this.items.toMutableList().apply { add(item) }
        }
    }

    fun addItem(index: Int, item: M) {
        if (searchable) {
            this.itemsOriginals = this.itemsOriginals.toMutableList().apply { add(index, item) }
            performSearch(lastQuery)
        } else {
            this.items = this.items.toMutableList().apply { add(index, item) }
        }
    }

    fun performSearch(query: String) {
        lastQuery = query
        searchFilter?.filter(lastQuery)
    }

    interface ItemClickListener<M> {
        fun onItemClick(item: M)
    }

    inner class ViewHolder<M>(val viewBinding: VB) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(items[layoutPosition])
            }
        }
    }

    private fun RecyclerView.Adapter<*>.autoNotify(oldList: List<M>, newList: List<M>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return equals(oldList[oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
    }

    private fun setupSearch() {
        searchFilter = object : Filter() {
            override fun performFiltering(filter: CharSequence): FilterResults {
                val filteredLocalList =
                    if (filter.toString().isEmpty()) {
                        itemsOriginals
                    } else {
                        itemsOriginals.filter {
                            it.toString().toLowerCase().contains(filter.toString().toLowerCase())
                        }
                    }
                val filterResults = FilterResults()
                filterResults.values = filteredLocalList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                if (filterResults.values != null) {
                    itemsFiltered = filterResults.values as List<M>
                    items = itemsFiltered.toList()
                }
            }
        }
    }
}