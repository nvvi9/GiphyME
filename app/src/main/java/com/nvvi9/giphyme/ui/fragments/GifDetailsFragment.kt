package com.nvvi9.giphyme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.FragmentGifDetailsBinding
import com.nvvi9.giphyme.ui.MainActivity
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment
import com.nvvi9.giphyme.ui.listeners.GifItemDetailsListener
import com.nvvi9.giphyme.ui.viewmodels.GifDetailsViewModel
import com.nvvi9.giphyme.vo.GifDetailsItem.GifAuthorDetailsItem
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class GifDetailsFragment : BaseFragment(), GifItemDetailsListener {

    private lateinit var binding: FragmentGifDetailsBinding

    private val gifDetailsViewModel: GifDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifDetailsBinding.inflate(inflater)
        binding.listener = this
        binding.lifecycleOwner = this
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_browser -> {
                    binding.details?.let {
                        openBrowser(it.url)
                    }
                    true
                }
                R.id.menu_copy -> {
                    binding.details?.let {
                        copyToClipboard(it.url)
                        showSnackbar(binding.root, R.string.copied_to_clipboard)
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
        arguments?.getString(GIF_ID_KEY)?.let { gifId ->
            disposable.add(
                gifDetailsViewModel.getGifDetails(gifId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        binding.details = it
                    }, {
                        Log.e(TAG, "error receiving item", it)
                        showError()
                    }, { Log.d(TAG, "completed") })
            )
        } ?: showError()
        return binding.root
    }

    override fun onProfileClicked(authorDetails: GifAuthorDetailsItem) {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.startBrowserIntent(authorDetails.profileUrl)
    }

    private fun showError() {
        showSnackbar(binding.root, R.string.error)
    }

    companion object {
        const val GIF_ID_KEY = "gifId"
        private const val TAG = "GifDetailsFragment"
    }
}