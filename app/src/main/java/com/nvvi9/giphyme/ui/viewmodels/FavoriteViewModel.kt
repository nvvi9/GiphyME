package com.nvvi9.giphyme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.nvvi9.giphyme.domain.FavoriteGifsUseCase
import com.nvvi9.giphyme.vo.FavoriteGifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteGifsUseCase: FavoriteGifsUseCase) :
    ViewModel() {

    val favoriteGifItems: Flowable<PagingData<FavoriteGifItem>>
        get() = favoriteGifsUseCase.getFavorites()

    fun deleteFromFavorites(gifId: String): Completable =
        favoriteGifsUseCase.deleteFromFavorites(gifId)
}