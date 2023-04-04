package com.nvvi9.giphyme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.CombinedLoadStates
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.FragmentSearchResultsBinding
import com.nvvi9.giphyme.ui.adapters.GifItemAdapter
import com.nvvi9.giphyme.ui.adapters.LoadStateFooterAdapter
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment
import com.nvvi9.giphyme.ui.listeners.GifItemListener
import com.nvvi9.giphyme.ui.viewmodels.SearchResultsViewModel
import com.nvvi9.giphyme.utils.SpringAddItemAnimator
import com.nvvi9.giphyme.vo.GifItem
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class SearchResultsFragment : BaseFragment(), GifItemListener {

    private val searchResultsViewModel: SearchResultsViewModel by viewModels()

    private lateinit var binding: FragmentSearchResultsBinding
    private lateinit var gifItemAdapter: GifItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultsBinding.inflate(inflater)
        gifItemAdapter = GifItemAdapter(this)
        val itemGifView = binding.itemGifView
        itemGifView.adapter = gifItemAdapter.withLoadStateFooter(LoadStateFooterAdapter())
        itemGifView.itemAnimator = SpringAddItemAnimator()
        binding.lifecycleOwner = this
        binding.searchQuery.setOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        binding.searchToolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }
        gifItemAdapter.addLoadStateListener { combinedLoadStates: CombinedLoadStates ->
            binding.loadState = combinedLoadStates.refresh
        }

        arguments?.getString(QUERY_KEY)?.let {
            binding.searchQuery.setText(it)
            searchResultsViewModel.updateQuery(it)
        } ?: showSnackbar(binding.root, R.string.error)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(
            searchResultsViewModel.gifs
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    gifItemAdapter.submitData(lifecycle, it)
                }, {
                    Log.e(TAG, "Error while receiving gifs by query", it)
                    showSnackbar(binding.root, R.string.error)
                }, { Log.d(TAG, "completed") })
        )
    }

    override fun onItemClicked(gifItem: GifItem) {
        openBrowser(gifItem.url)
    }

    override fun onItemLongClicked(gifItem: GifItem): Boolean {
        showMenuBottomSheetDialog(
            if (gifItem.inFavourites) R.menu.favorite_gif_item_bottom_sheet_menu else R.menu.gif_item_bottom_sheet_menu
        ) {
            when (it.itemId) {
                R.id.menu_save -> {
                    addToFavorites(gifItem.id)
                    true
                }
                R.id.menu_browser -> {
                    openBrowser(gifItem.url)
                    true
                }
                R.id.menu_copy -> {
                    copyToClipboard(gifItem.url)
                    showSnackbar(binding.root, R.string.copied_to_clipboard)
                    true
                }
                else -> false
            }
        }
        return true
    }

    private fun addToFavorites(gifId: String) {
        disposable.add(
            searchResultsViewModel.saveToFavourites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showSnackbar(binding.root, R.string.added_to_favorites) }, {
                    Log.e(TAG, "Error while adding to favorites", it)
                    showSnackbar(binding.root, R.string.error_adding_to_favorites)
                })
        )
    }

    companion object {
        const val QUERY_KEY = "query"
        private const val TAG = "SearchResultsFragment"
    }
}