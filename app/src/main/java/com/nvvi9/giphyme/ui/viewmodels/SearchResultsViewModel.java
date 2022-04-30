package com.nvvi9.giphyme.ui.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.nvvi9.giphyme.domain.SearchResultsUseCase;
import com.nvvi9.giphyme.vo.GifItem;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;

@HiltViewModel
public class SearchResultsViewModel extends ViewModel {

    private final SearchResultsUseCase searchResultsUseCase;

    private final Subject<String> querySubject = BehaviorSubject.create();

    @Inject
    public SearchResultsViewModel(SearchResultsUseCase searchResultsUseCase) {
        this.searchResultsUseCase = searchResultsUseCase;
    }

    public Flowable<PagingData<GifItem>> getGifs() {
        return querySubject.toFlowable(BackpressureStrategy.LATEST)
                .switchMap(searchResultsUseCase::getGifsFromQuery);
    }

    public void updateQuery(String query) {
        querySubject.onNext(query);
    }

    public Completable saveToFavourites(String gifId) {
        return searchResultsUseCase.saveInFavorites(gifId);
    }
}
