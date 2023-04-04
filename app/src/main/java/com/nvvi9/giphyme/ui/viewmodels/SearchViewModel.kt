package com.nvvi9.giphyme.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.nvvi9.giphyme.domain.SearchUseCase
import com.nvvi9.giphyme.vo.SuggestionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val querySubject: Subject<String> = BehaviorSubject.create()

    fun updateQuery(query: String) {
        querySubject.onNext(query)
    }

    val suggestions: Flowable<SuggestionItem>
        get() = querySubject.toFlowable(BackpressureStrategy.LATEST)
            .debounce(300, TimeUnit.MILLISECONDS)
            .switchMapSingle { query ->
                searchUseCase.getSuggestion(query)
            }
}