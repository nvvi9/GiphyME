package com.nvvi9.giphyme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.nvvi9.giphyme.domain.TrendingGifsUseCase
import com.nvvi9.giphyme.vo.GifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val trendingGifsUseCase: TrendingGifsUseCase) :
    ViewModel() {

    val trending: Flowable<PagingData<GifItem>>
        get() = trendingGifsUseCase.getTrendingGifs()

    fun addToFavorites(gifId: String): Completable =
        trendingGifsUseCase.addToFavorites(gifId)

    fun removeFromFavorites(gifId: String): Completable =
        trendingGifsUseCase.removeFromFavorites(gifId)
}