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

import com.nvvi9.giphyme.R;
import com.nvvi9.giphyme.databinding.FragmentFavoriteBinding;
import com.nvvi9.giphyme.ui.adapters.FavoriteGifItemAdapter;
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment;
import com.nvvi9.giphyme.ui.listeners.FavoriteGifItemListener;
import com.nvvi9.giphyme.ui.viewmodels.FavoriteViewModel;
import com.nvvi9.giphyme.utils.SpringAddItemAnimator;
import com.nvvi9.giphyme.vo.FavoriteGifItem;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class FavoriteFragment extends BaseFragment implements FavoriteGifItemListener {

    private final static String TAG = "FavoriteFragment";

    private FragmentFavoriteBinding binding;

    private FavoriteViewModel favoriteViewModel;

    private FavoriteGifItemAdapter favoriteGifItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater);
        favoriteGifItemAdapter = new FavoriteGifItemAdapter(this);
        binding.itemGifView.setAdapter(favoriteGifItemAdapter);
        binding.itemGifView.setItemAnimator(new SpringAddItemAnimator());
        binding.setLifecycleOwner(this);

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposable.add(favoriteViewModel.getFavoriteGifItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favoriteGifItemPagingData -> favoriteGifItemAdapter.submitData(getLifecycle(), favoriteGifItemPagingData),
                        throwable -> {
                            Log.e(TAG, "Error while receiving favorite gifs", throwable);
                            showSnackbar(binding.getRoot(), R.string.error_receiving_favorites);
                        },
                        () -> Log.d(TAG, "completed")
                ));
    }

    @Override
    public void onItemClick(FavoriteGifItem gifItem) {
        Bundle b = new Bundle();
        b.putString(GifDetailsFragment.GIF_ID_KEY, gifItem.getId());
        NavHostFragment.findNavController(this).navigate(R.id.action_favoriteFragment_to_gifDetailsFragment, b);
    }

    @Override
    public boolean onItemLongClick(FavoriteGifItem gifItem) {
        showMenuBottomSheetDialog(R.menu.favorite_gif_item_bottom_sheet_menu, menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.menu_delete) {
                deleteFromFavorites(gifItem.getId());
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

    private void deleteFromFavorites(String gifId) {
        disposable.add(favoriteViewModel.deleteFromFavorites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> showSnackbar(binding.getRoot(), R.string.removed_from_favorites),
                        throwable -> {
                            Log.e(TAG, "Error while removing from favorites", throwable);
                            showSnackbar(binding.getRoot(), R.string.error_removing_favorites);
                        }));
    }
}
