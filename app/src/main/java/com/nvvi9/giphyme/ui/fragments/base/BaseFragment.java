package com.nvvi9.giphyme.ui.fragments.base;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.MenuRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.nvvi9.giphyme.ui.MainActivity;
import com.nvvi9.giphyme.ui.fragments.MenuBottomSheetDialogFragment;

import java.util.function.Function;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseFragment extends Fragment {

    protected final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onDestroyView() {
        disposable.clear();
        super.onDestroyView();
    }

    protected void copyToClipboard(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text));
    }

    protected void showSnackbar(View view, @StringRes int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    protected void openBrowser(String url) {
        MainActivity activity = (MainActivity) requireActivity();
        activity.startBrowserIntent(url);
    }

    protected void showMenuBottomSheetDialog(@MenuRes int menuRes, Function<MenuItem, Boolean> onMenuItemSelected) {
        MenuBottomSheetDialogFragment dialogFragment = new MenuBottomSheetDialogFragment(menuRes, onMenuItemSelected);
        dialogFragment.show(getParentFragmentManager(), null);
    }
}
