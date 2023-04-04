package com.nvvi9.giphyme.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.nvvi9.giphyme.domain.SearchResultsUseCase
import com.nvvi9.giphyme.vo.GifItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(private val searchResultsUseCase: SearchResultsUseCase) :
    ViewModel() {

    private val querySubject: Subject<String> = BehaviorSubject.create()

    val gifs: Flowable<PagingData<GifItem>>
        get() = querySubject.toFlowable(BackpressureStrategy.LATEST)
            .switchMap { query ->
                searchResultsUseCase.getGifsFromQuery(query)
            }

    fun updateQuery(query: String) {
        querySubject.onNext(query)
    }

    fun saveToFavourites(gifId: String): Completable =
        searchResultsUseCase.saveInFavorites(gifId)
}