package com.nvvi9.giphyme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.FragmentTrendingBinding
import com.nvvi9.giphyme.ui.adapters.GifItemAdapter
import com.nvvi9.giphyme.ui.adapters.LoadStateFooterAdapter
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment
import com.nvvi9.giphyme.ui.listeners.GifItemListener
import com.nvvi9.giphyme.ui.viewmodels.TrendingViewModel
import com.nvvi9.giphyme.utils.SpringAddItemAnimator
import com.nvvi9.giphyme.vo.GifItem
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class TrendingFragment : BaseFragment(), GifItemListener {

    private val trendingViewModel: TrendingViewModel by viewModels()

    private lateinit var binding: FragmentTrendingBinding
    private lateinit var gifItemAdapter: GifItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater)
        gifItemAdapter = GifItemAdapter(this)
        val itemGifView = binding.itemGifView
        itemGifView.adapter = gifItemAdapter.withLoadStateFooter(LoadStateFooterAdapter())
        itemGifView.itemAnimator = SpringAddItemAnimator()
        binding.lifecycleOwner = this
        gifItemAdapter.refresh()
        binding.swipeRefresh.setOnRefreshListener { gifItemAdapter.refresh() }
        gifItemAdapter.addLoadStateListener {
            binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading
            showSnackbar(binding.swipeRefresh, R.string.error)
        }
        binding.searchButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_trendingFragment_to_searchFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(
            trendingViewModel.trending
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    gifItemAdapter.submitData(lifecycle, it)
                }, {
                    Log.e(TAG, "error while receiving trending gifs", it)
                    showSnackbar(binding.root, R.string.error)
                }, { Log.d(TAG, "completed") })
        )
    }

    override fun onItemClicked(gifItem: GifItem) {
        openBrowser(gifItem.url)
    }

    override fun onItemLongClicked(gifItem: GifItem): Boolean {
        showMenuBottomSheetDialog(R.menu.gif_item_bottom_sheet_menu) {
            when (it.itemId) {
                R.id.menu_save -> {
                    addToFavorites(gifItem.id)
                    true
                }
                R.id.menu_delete -> {
                    removeFromFavorites(gifItem.id)
                    true
                }
                R.id.menu_browser -> {
                    openBrowser(gifItem.url)
                    true
                }
                R.id.menu_copy -> {
                    copyToClipboard(gifItem.url)
                    showSnackbar(binding.swipeRefresh, R.string.copied_to_clipboard)
                    true
                }
                else -> false
            }
        }
        return true
    }

    private fun removeFromFavorites(gifId: String) {
        disposable.add(
            trendingViewModel.removeFromFavorites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { showSnackbar(binding.swipeRefresh, R.string.removed_from_favorites) }
                ) {
                    Log.e(TAG, "error removing form favorites", it)
                    showSnackbar(binding.swipeRefresh, R.string.error_removing_favorites)
                })
    }

    private fun addToFavorites(gifId: String) {
        disposable.add(
            trendingViewModel.addToFavorites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showSnackbar(binding.swipeRefresh, R.string.added_to_favorites) }, {
                    Log.e(TAG, "error adding to favorites", it)
                    showSnackbar(binding.swipeRefresh, R.string.error_adding_to_favorites)
                })
        )
    }

    companion object {
        private const val TAG = "TrendingFragment"
    }
}