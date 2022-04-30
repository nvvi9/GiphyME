package com.nvvi9.giphyme.ui.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.nvvi9.giphyme.domain.TrendingGifsUseCase;
import com.nvvi9.giphyme.vo.GifItem;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class TrendingViewModel extends ViewModel {

    private final TrendingGifsUseCase trendingGifsUseCase;

    @Inject
    public TrendingViewModel(TrendingGifsUseCase trendingGifsUseCase) {
        this.trendingGifsUseCase = trendingGifsUseCase;
    }

    public Flowable<PagingData<GifItem>> getTrending() {
        return trendingGifsUseCase.getTrendingGifs();
    }

    public Completable addToFavorites(String gifId) {
        return trendingGifsUseCase.addToFavorites(gifId);
    }

    public Completable removeFromFavorites(String gifId) {
        return trendingGifsUseCase.removeFromFavorites(gifId);
    }
}
