package com.te0tl.commons.presentation.view

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.te0tl.commons.platform.extension.android.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlin.properties.Delegates

abstract class BaseRecyclerViewAdapter<M>(private val searchable : Boolean = false) : RecyclerView.Adapter<BaseRecyclerViewAdapter<M>.ViewHolder<M>>() {

    abstract val itemResourceId : Int

    abstract val bindData: (holder: ViewHolder<M>, model: M) -> Unit

    abstract val areItemsTheSame : (itemOne : M, itemTwo: M) -> Boolean


    var itemClickListener : ItemClickListener<M>? = null

    var items: List<M> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList)
    }

    private var itemsOriginals = emptyList<M>()
    private var itemsFiltered = emptyList<M>()

    private var searchFilter : Filter? = null
    private var lastQuery = ""

    init {
        if (searchable) {
            itemsFiltered = items.toMutableList()
            itemsOriginals = items.toMutableList()
            setupSearch()
        }
    }

    fun clearItems() {
        this.items = emptyList()

        if (searchable) {
            itemsFiltered = items.toMutableList()
            itemsOriginals = items.toMutableList()
            performSearch(lastQuery)
        }
    }

    fun updateItems(items : List<M>) {
        if (searchable) {
            this.itemsOriginals = items.toMutableList()
            performSearch(lastQuery)
        } else {
            this.items = items.toMutableList()
        }
    }

    fun addItem(item : M) {
        if (searchable) {
            this.itemsOriginals = this.itemsOriginals.toMutableList().apply { add(item) }
            performSearch(lastQuery)
        } else {
            this.items = this.items.toMutableList().apply { add(item) }
        }
    }

    fun addItem(index : Int, item : M) {
        if (searchable) {
            this.itemsOriginals = this.itemsOriginals.toMutableList().apply { add(index, item) }
            performSearch(lastQuery)
        } else {
            this.items = this.items.toMutableList().apply { add(index, item) }
        }
    }

    fun performSearch(query : String) {
        lastQuery = query
        searchFilter?.filter(lastQuery)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder<M> {
        return ViewHolder(parent.inflate(itemResourceId))
    }

    override fun onBindViewHolder(holder: ViewHolder<M>, position: Int) {
        holder.bindData(holder, items[position], bindData)
    }

    inner class ViewHolder<M>(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(items[layoutPosition])
            }
        }

        fun bindData(viewHolder : ViewHolder<M>, model : M, bindDataCode: (viewHolder : ViewHolder<M>, model: M) -> Unit) {
            bindDataCode(viewHolder, model)
        }
    }

    interface ItemClickListener<M> {
        fun onItemClick(item : M)
    }

    private fun RecyclerView.Adapter<*>.autoNotify(oldList: List<M>, newList: List<M>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])
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
                    itemsOriginals.filter { it.toString().toLowerCase().contains(filter.toString().toLowerCase()) }
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