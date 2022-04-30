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
import com.nvvi9.giphyme.databinding.FragmentGifDetailsBinding;
import com.nvvi9.giphyme.ui.MainActivity;
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment;
import com.nvvi9.giphyme.ui.listeners.GifItemDetailsListener;
import com.nvvi9.giphyme.ui.viewmodels.GifDetailsViewModel;
import com.nvvi9.giphyme.vo.GifDetailsItem;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class GifDetailsFragment extends BaseFragment implements GifItemDetailsListener {

    public static final String GIF_ID_KEY = "gifId";

    private static final String TAG = "GifDetailsFragment";

    private FragmentGifDetailsBinding binding;

    private GifDetailsViewModel gifDetailsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGifDetailsBinding.inflate(inflater);
        binding.setListener(this);
        binding.setLifecycleOwner(this);

        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_browser) {
                openBrowser(binding.getDetails().getUrl());
                return true;
            } else if (itemId == R.id.menu_copy) {
                copyToClipboard(binding.getDetails().getUrl());
                showSnackbar(binding.getRoot(), R.string.copied_to_clipboard);
                return true;
            }
            return false;
        });
        gifDetailsViewModel = new ViewModelProvider(this).get(GifDetailsViewModel.class);


        Bundle arguments = getArguments();

        if (arguments != null) {
            String gifId = arguments.getString(GIF_ID_KEY);
            if (gifId != null) {
                disposable.add(gifDetailsViewModel.getGifDetails(gifId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                gifDetailsItem -> binding.setDetails(gifDetailsItem),
                                throwable -> {
                                    Log.e(TAG, "error receiving item", throwable);
                                    showError();
                                },
                                () -> Log.d(TAG, "completed")
                        ));
            } else {
                showError();
            }
        } else {
            showError();
        }

        return binding.getRoot();
    }

    @Override
    public void onProfileClicked(GifDetailsItem.GifAuthorDetailsItem authorDetails) {
        String profileUrl = authorDetails.getProfileUrl();
        if (profileUrl != null) {
            MainActivity mainActivity = (MainActivity) requireActivity();
            mainActivity.startBrowserIntent(profileUrl);
        }
    }

    private void showError() {
        showSnackbar(binding.getRoot(), R.string.error);
    }
}
