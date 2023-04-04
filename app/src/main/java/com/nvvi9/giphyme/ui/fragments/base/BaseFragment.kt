package com.nvvi9.giphyme.ui.fragments.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.nvvi9.giphyme.ui.MainActivity
import com.nvvi9.giphyme.ui.fragments.MenuBottomSheetDialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    @JvmField
    protected val disposable = CompositeDisposable()

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    protected fun copyToClipboard(text: String) {
        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text))
    }

    protected fun showSnackbar(view: View, @StringRes resId: Int) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show()
    }

    protected fun openBrowser(url: String) {
        val activity = requireActivity() as MainActivity
        activity.startBrowserIntent(url)
    }

    protected fun showMenuBottomSheetDialog(
        @MenuRes menuRes: Int,
        onMenuItemSelected: (MenuItem) -> Boolean
    ) {
        MenuBottomSheetDialogFragment(menuRes, onMenuItemSelected)
            .show(parentFragmentManager, null)
    }
}