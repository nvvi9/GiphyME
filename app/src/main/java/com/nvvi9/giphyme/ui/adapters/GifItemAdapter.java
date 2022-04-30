package com.nvvi9.giphyme.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nvvi9.giphyme.R;
import com.nvvi9.giphyme.databinding.ItemGifBinding;
import com.nvvi9.giphyme.ui.listeners.GifItemListener;
import com.nvvi9.giphyme.vo.GifItem;

public class GifItemAdapter extends PagingDataAdapter<GifItem, GifItemAdapter.GifItemViewHolder> {

    private final GifItemListener listener;

    public GifItemAdapter(GifItemListener listener) {
        super(new GifItemComparator());
        this.listener = listener;
    }

    @NonNull
    @Override
    public GifItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GifItemViewHolder(ItemGifBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GifItemViewHolder holder, int position) {
        GifItem item = getItem(position);
        if (item != null) {
            holder.bind(item);
        }
    }

    static class GifItemViewHolder extends RecyclerView.ViewHolder {

        private final float addedCornerSize = itemView.getResources().getDimension(R.dimen.small_component_corner_radius);
        private final ItemGifBinding binding;

        public GifItemViewHolder(@NonNull ItemGifBinding binding, GifItemListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(listener);
        }

        public void bind(GifItem gifItem) {
            binding.setGifItem(gifItem);
            binding.getRoot().setActivated(gifItem.getInFavourites());
            updateCardViewBottomLeftCornerSize(gifItem.getInFavourites() ? 1f : 0f);
            binding.executePendingBindings();
        }

        private void updateCardViewBottomLeftCornerSize(float interpolation) {
            MaterialCardView cardView = binding.cardView;
            cardView.setShapeAppearanceModel(
                    cardView.getShapeAppearanceModel().toBuilder()
                            .setBottomLeftCornerSize(interpolation * addedCornerSize)
                            .build()
            );
        }
    }

    private static class GifItemComparator extends DiffUtil.ItemCallback<GifItem> {

        @Override
        public boolean areItemsTheSame(@NonNull GifItem oldItem, @NonNull GifItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull GifItem oldItem, @NonNull GifItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
