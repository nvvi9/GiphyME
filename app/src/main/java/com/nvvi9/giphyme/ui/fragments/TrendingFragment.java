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
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import com.nvvi9.giphyme.R;
import com.nvvi9.giphyme.databinding.FragmentTrendingBinding;
import com.nvvi9.giphyme.ui.adapters.GifItemAdapter;
import com.nvvi9.giphyme.ui.adapters.LoadStateFooterAdapter;
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment;
import com.nvvi9.giphyme.ui.listeners.GifItemListener;
import com.nvvi9.giphyme.ui.viewmodels.TrendingViewModel;
import com.nvvi9.giphyme.utils.SpringAddItemAnimator;
import com.nvvi9.giphyme.vo.GifItem;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

@AndroidEntryPoint
public class TrendingFragment extends BaseFragment implements GifItemListener {

    private static final String TAG = "TrendingFragment";

    private TrendingViewModel trendingViewModel;

    private FragmentTrendingBinding binding;

    private GifItemAdapter gifItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrendingBinding.inflate(inflater);
        gifItemAdapter = new GifItemAdapter(this);
        RecyclerView itemGifView = binding.itemGifView;
        itemGifView.setAdapter(gifItemAdapter.withLoadStateFooter(new LoadStateFooterAdapter()));
        itemGifView.setItemAnimator(new SpringAddItemAnimator());
        binding.setLifecycleOwner(this);
        gifItemAdapter.refresh();

        binding.swipeRefresh.setOnRefreshListener(() -> gifItemAdapter.refresh());

        gifItemAdapter.addLoadStateListener(combinedLoadStates -> {
            binding.swipeRefresh.setRefreshing(combinedLoadStates.getRefresh() instanceof LoadState.Loading);
            if (combinedLoadStates.getRefresh() instanceof LoadState.Error
                    || combinedLoadStates.getAppend() instanceof LoadState.Error
                    || combinedLoadStates.getPrepend() instanceof LoadState.Error) {
                showSnackbar(binding.swipeRefresh, R.string.error);
            }
            return Unit.INSTANCE;
        });

        binding.searchButton.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_trendingFragment_to_searchFragment));

        trendingViewModel = new ViewModelProvider(this).get(TrendingViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposable.add(trendingViewModel.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gifItemPagingData -> gifItemAdapter.submitData(getLifecycle(), gifItemPagingData),
                        throwable -> {
                            Log.e(TAG, "error while receiving trending gifs", throwable);
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
                R.menu.gif_item_bottom_sheet_menu,
                menuItem -> {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.menu_save) {
                        addToFavorites(gifItem.getId());
                        return true;
                    } else if (itemId == R.id.menu_delete) {
                        removeFromFavorites(gifItem.getId());
                        return true;
                    } else if (itemId == R.id.menu_browser) {
                        openBrowser(gifItem.getUrl());
                        return true;
                    } else if (itemId == R.id.menu_copy) {
                        copyToClipboard(gifItem.getUrl());
                        showSnackbar(binding.swipeRefresh, R.string.copied_to_clipboard);
                        return true;
                    }
                    return false;
                });

        return true;
    }

    private void removeFromFavorites(String gifId) {
        disposable.add(trendingViewModel.removeFromFavorites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> showSnackbar(binding.swipeRefresh, R.string.removed_from_favorites),
                        throwable -> {
                            Log.e(TAG, "error removing form favorites", throwable);
                            showSnackbar(binding.swipeRefresh, R.string.error_removing_favorites);
                        }));
    }

    private void addToFavorites(String gifId) {
        disposable.add(trendingViewModel.addToFavorites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> showSnackbar(binding.swipeRefresh, R.string.added_to_favorites),
                        throwable -> {
                            Log.e(TAG, "error adding to favorites", throwable);
                            showSnackbar(binding.swipeRefresh, R.string.error_adding_to_favorites);
                        }));
    }
}
