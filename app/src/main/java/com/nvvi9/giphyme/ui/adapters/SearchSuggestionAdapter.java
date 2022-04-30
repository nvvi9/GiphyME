package com.nvvi9.giphyme.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nvvi9.giphyme.databinding.ItemSearchBinding;
import com.nvvi9.giphyme.ui.listeners.SearchSuggestionsListener;

public class SearchSuggestionAdapter extends ListAdapter<String, SearchSuggestionAdapter.ViewHolder> {

    private final SearchSuggestionsListener listener;

    public SearchSuggestionAdapter(SearchSuggestionsListener listener) {
        super(new SuggestionsComparator());
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestionAdapter.ViewHolder holder, int position) {
        String item = getItem(position);
        if (item != null) {
            holder.bind(item);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemSearchBinding binding;

        public ViewHolder(ItemSearchBinding binding, SearchSuggestionsListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setListener(listener);
        }

        public void bind(String item) {
            binding.setData(item);
            binding.executePendingBindings();
        }
    }

    static class SuggestionsComparator extends DiffUtil.ItemCallback<String> {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    }
}
