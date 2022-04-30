package com.nvvi9.giphyme.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nvvi9.giphyme.databinding.ItemFavoriteGifBinding;
import com.nvvi9.giphyme.ui.listeners.FavoriteGifItemListener;
import com.nvvi9.giphyme.vo.FavoriteGifItem;

public class FavoriteGifItemAdapter extends PagingDataAdapter<FavoriteGifItem, FavoriteGifItemAdapter.ViewHolder> {

    private final FavoriteGifItemListener listener;

    public FavoriteGifItemAdapter(FavoriteGifItemListener listener) {
        super(new FavoriteGifItemComparator());
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteGifItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFavoriteGifBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteGifItemAdapter.ViewHolder holder, int position) {
        FavoriteGifItem item = getItem(position);
        if (item != null) {
            holder.bind(item);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFavoriteGifBinding binding;

        public ViewHolder(ItemFavoriteGifBinding binding, FavoriteGifItemListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(listener);
        }

        public void bind(FavoriteGifItem item) {
            binding.setGifItem(item);
            binding.executePendingBindings();
        }
    }

    private static class FavoriteGifItemComparator extends DiffUtil.ItemCallback<FavoriteGifItem> {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteGifItem oldItem, @NonNull FavoriteGifItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteGifItem oldItem, @NonNull FavoriteGifItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
