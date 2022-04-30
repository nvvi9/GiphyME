package com.nvvi9.giphyme.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nvvi9.giphyme.R;
import com.nvvi9.giphyme.databinding.FragmentSearchResultsBinding;
import com.nvvi9.giphyme.ui.adapters.GifItemAdapter;
import com.nvvi9.giphyme.ui.adapters.LoadStateFooterAdapter;
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment;
import com.nvvi9.giphyme.ui.listeners.GifItemListener;
import com.nvvi9.giphyme.ui.viewmodels.SearchResultsViewModel;
import com.nvvi9.giphyme.utils.SpringAddItemAnimator;
import com.nvvi9.giphyme.vo.GifItem;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

@AndroidEntryPoint
public class SearchResultsFragment extends BaseFragment implements GifItemListener {

    public final static String QUERY_KEY = "query";

    private final static String TAG = "SearchResultsFragment";

    private SearchResultsViewModel searchResultsViewModel;

    private FragmentSearchResultsBinding binding;

    private GifItemAdapter gifItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchResultsBinding.inflate(inflater);

        gifItemAdapter = new GifItemAdapter(this);
        RecyclerView itemGifView = binding.itemGifView;
        itemGifView.setAdapter(gifItemAdapter.withLoadStateFooter(new LoadStateFooterAdapter()));
        itemGifView.setItemAnimator(new SpringAddItemAnimator());

        binding.setLifecycleOwner(this);

        binding.searchQuery.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
        binding.searchToolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        gifItemAdapter.addLoadStateListener(combinedLoadStates -> {
            binding.setLoadState(combinedLoadStates.getRefresh());
            return Unit.INSTANCE;
        });

        searchResultsViewModel = new ViewModelProvider(this).get(SearchResultsViewModel.class);

        Bundle arguments = getArguments();

        if (arguments != null) {
            String query = arguments.getString(QUERY_KEY);
            if (query != null) {
                binding.searchQuery.setText(query);
                searchResultsViewModel.updateQuery(query);
            } else {
                showSnackbar(binding.getRoot(), R.string.error);
            }
        } else {
            showSnackbar(binding.getRoot(), R.string.error);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposable.add(searchResultsViewModel.getGifs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gifItemPagingData -> gifItemAdapter.submitData(getLifecycle(), gifItemPagingData),
                        throwable -> {
                            Log.e(TAG, "Error while receiving gifs by query", throwable);
                            showSnackbar(binding.getRoot(), R.string.error);
                        },
                        () -> Log.d(TAG, "completed")
                ));
    }

    @Override
    public void onItemClicked(GifItem gifItem) {
        openBrowser(gifItem.getUrl());
    }

    @Override
    public boolean onItemLongClicked(GifItem gifItem) {
        showMenuBottomSheetDialog(
                gifItem.getInFavourites() ? R.menu.favorite_gif_item_bottom_sheet_menu : R.menu.gif_item_bottom_sheet_menu,
                menuItem -> {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.menu_save) {
                        addToFavorites(gifItem.getId());
                        return true;
                    } else if (itemId == R.id.menu_browser) {
                        openBrowser(gifItem.getUrl());
                        return true;
                    } else if (itemId == R.id.menu_copy) {
                        copyToClipboard(gifItem.getUrl());
                        showSnackbar(binding.getRoot(), R.string.copied_to_clipboard);
                        return true;
                    }
                    return false;
                });

        return true;
    }

    private void addToFavorites(String gifId) {
        disposable.add(searchResultsViewModel.saveToFavourites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> showSnackbar(binding.getRoot(), R.string.added_to_favorites),
                        throwable -> {
                            Log.e(TAG, "Error while adding to favorites", throwable);
                            showSnackbar(binding.getRoot(), R.string.error_adding_to_favorites);
                        }));
    }
}
