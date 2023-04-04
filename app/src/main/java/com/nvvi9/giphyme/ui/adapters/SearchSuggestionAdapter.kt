package com.nvvi9.giphyme.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nvvi9.giphyme.databinding.ItemSearchBinding
import com.nvvi9.giphyme.ui.listeners.SearchSuggestionsListener

class SearchSuggestionAdapter(private val listener: SearchSuggestionsListener) :
    ListAdapter<String, SearchSuggestionAdapter.ViewHolder>(SuggestionsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemSearchBinding, listener: SearchSuggestionsListener) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = listener
        }

        fun bind(item: String) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    object SuggestionsComparator : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}