package com.nvvi9.giphyme.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nvvi9.giphyme.databinding.ItemFavoriteGifBinding
import com.nvvi9.giphyme.ui.listeners.FavoriteGifItemListener
import com.nvvi9.giphyme.vo.FavoriteGifItem

class FavoriteGifItemAdapter(private val listener: FavoriteGifItemListener) :
    PagingDataAdapter<FavoriteGifItem, FavoriteGifItemAdapter.ViewHolder>(FavoriteGifItemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemFavoriteGifBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(
        private val binding: ItemFavoriteGifBinding,
        listener: FavoriteGifItemListener
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.listener = listener
        }

        fun bind(item: FavoriteGifItem) {
            binding.gifItem = item
            binding.executePendingBindings()
        }
    }

    object FavoriteGifItemComparator : DiffUtil.ItemCallback<FavoriteGifItem>() {

        override fun areItemsTheSame(oldItem: FavoriteGifItem, newItem: FavoriteGifItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FavoriteGifItem,
            newItem: FavoriteGifItem
        ): Boolean = oldItem == newItem
    }
}