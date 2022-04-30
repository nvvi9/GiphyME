package com.nvvi9.giphyme.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nvvi9.giphyme.databinding.ItemLoadStateFooterBinding;

public class LoadStateFooterAdapter extends LoadStateAdapter<LoadStateFooterAdapter.LoadStateFooterViewHolder> {
    @Override
    public void onBindViewHolder(@NonNull LoadStateFooterAdapter.LoadStateFooterViewHolder loadStateFooterViewHolder, @NonNull LoadState loadState) {
        loadStateFooterViewHolder.bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateFooterAdapter.LoadStateFooterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, @NonNull LoadState loadState) {
        return new LoadStateFooterViewHolder(ItemLoadStateFooterBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    static class LoadStateFooterViewHolder extends RecyclerView.ViewHolder {

        private final ItemLoadStateFooterBinding binding;

        public LoadStateFooterViewHolder(ItemLoadStateFooterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LoadState loadState) {
            binding.progressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
        }
    }
}
