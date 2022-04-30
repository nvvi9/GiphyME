package com.nvvi9.giphyme.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.nvvi9.giphyme.domain.SearchUseCase;
import com.nvvi9.giphyme.vo.SuggestionItem;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    private final Subject<String> querySubject = BehaviorSubject.create();

    private final SearchUseCase searchUseCase;

    @Inject
    public SearchViewModel(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    public void updateQuery(String query) {
        querySubject.onNext(query);
    }

    public Flowable<SuggestionItem> getSuggestions() {
        return querySubject.toFlowable(BackpressureStrategy.LATEST)
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMapSingle(searchUseCase::getSuggestion);
    }
}
