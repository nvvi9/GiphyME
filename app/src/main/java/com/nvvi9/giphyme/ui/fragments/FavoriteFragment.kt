package com.nvvi9.giphyme.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.PagingData
import com.nvvi9.giphyme.R
import com.nvvi9.giphyme.databinding.FragmentFavoriteBinding
import com.nvvi9.giphyme.ui.adapters.FavoriteGifItemAdapter
import com.nvvi9.giphyme.ui.fragments.base.BaseFragment
import com.nvvi9.giphyme.ui.listeners.FavoriteGifItemListener
import com.nvvi9.giphyme.ui.viewmodels.FavoriteViewModel
import com.nvvi9.giphyme.utils.SpringAddItemAnimator
import com.nvvi9.giphyme.vo.FavoriteGifItem
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class FavoriteFragment : BaseFragment(), FavoriteGifItemListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteGifItemAdapter: FavoriteGifItemAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        favoriteGifItemAdapter = FavoriteGifItemAdapter(this)
        binding.itemGifView.adapter = favoriteGifItemAdapter
        binding.itemGifView.itemAnimator = SpringAddItemAnimator()
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        disposable.add(
            favoriteViewModel.favoriteGifItems
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favoriteGifItemPagingData: PagingData<FavoriteGifItem> ->
                    favoriteGifItemAdapter.submitData(lifecycle, favoriteGifItemPagingData)
                }, {
                    Log.e(TAG, "Error while receiving favorite gifs", it)
                    showSnackbar(binding.root, R.string.error_receiving_favorites)
                }, { Log.d(TAG, "completed") })
        )
    }

    override fun onItemClick(gifItem: FavoriteGifItem) {
        val b = Bundle()
        b.putString(GifDetailsFragment.GIF_ID_KEY, gifItem.id)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_favoriteFragment_to_gifDetailsFragment, b)
    }

    override fun onItemLongClick(gifItem: FavoriteGifItem): Boolean {
        showMenuBottomSheetDialog(R.menu.favorite_gif_item_bottom_sheet_menu) {
            when (it.itemId) {
                R.id.menu_delete -> {
                    deleteFromFavorites(gifItem.id)
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

    private fun deleteFromFavorites(gifId: String) {
        disposable.add(
            favoriteViewModel.deleteFromFavorites(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showSnackbar(binding.root, R.string.removed_from_favorites)
                }, {
                    Log.e(TAG, "Error while removing from favorites", it)
                    showSnackbar(binding.root, R.string.error_removing_favorites)
                })
        )
    }

    companion object {
        private const val TAG = "FavoriteFragment"
    }
}