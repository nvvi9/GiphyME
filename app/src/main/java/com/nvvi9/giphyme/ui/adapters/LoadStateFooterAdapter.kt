package com.nvvi9.giphyme.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nvvi9.giphyme.databinding.ItemLoadStateFooterBinding
import com.nvvi9.giphyme.ui.adapters.LoadStateFooterAdapter.LoadStateFooterViewHolder

class LoadStateFooterAdapter : LoadStateAdapter<LoadStateFooterViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateFooterViewHolder =
        LoadStateFooterViewHolder(
            ItemLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    class LoadStateFooterViewHolder(private val binding: ItemLoadStateFooterBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(loadState: LoadState) {
            binding.progressBar.visibility =
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        }
    }
}