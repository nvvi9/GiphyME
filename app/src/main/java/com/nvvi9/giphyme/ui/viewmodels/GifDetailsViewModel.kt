package com.nvvi9.giphyme.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.nvvi9.giphyme.domain.GifDetailsUseCase
import com.nvvi9.giphyme.vo.GifDetailsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@HiltViewModel
class GifDetailsViewModel @Inject constructor(private val gifDetailsUseCase: GifDetailsUseCase) :
    ViewModel() {

    fun getGifDetails(gifId: String): Observable<GifDetailsItem> =
        gifDetailsUseCase.getGifDetails(gifId)
}