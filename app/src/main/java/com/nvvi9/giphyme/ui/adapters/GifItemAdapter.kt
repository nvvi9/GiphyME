package com.nvvi9.giphyme.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.ItemGifBinding
import com.nvvi9.giphyme.ui.adapters.GifItemAdapter.GifItemViewHolder
import com.nvvi9.giphyme.ui.listeners.GifItemListener
import com.nvvi9.giphyme.vo.GifItem

class GifItemAdapter(private val listener: GifItemListener) :
    PagingDataAdapter<GifItem, GifItemViewHolder>(GifItemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifItemViewHolder =
        GifItemViewHolder(
            ItemGifBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )

    override fun onBindViewHolder(holder: GifItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class GifItemViewHolder(
        private val binding: ItemGifBinding,
        listener: GifItemListener
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private val addedCornerSize =
            itemView.resources.getDimension(R.dimen.small_component_corner_radius)

        init {
            binding.listener = listener
        }

        fun bind(gifItem: GifItem) {
            binding.gifItem = gifItem
            binding.root.isActivated = gifItem.inFavourites
            updateCardViewBottomLeftCornerSize(if (gifItem.inFavourites) 1f else 0f)
            binding.executePendingBindings()
        }

        private fun updateCardViewBottomLeftCornerSize(interpolation: Float) {
            val cardView = binding.cardView
            cardView.shapeAppearanceModel = cardView.shapeAppearanceModel.toBuilder()
                .setBottomLeftCornerSize(interpolation * addedCornerSize)
                .build()
        }
    }

    object GifItemComparator : DiffUtil.ItemCallback<GifItem>() {
        override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean =
            oldItem == newItem
    }
}