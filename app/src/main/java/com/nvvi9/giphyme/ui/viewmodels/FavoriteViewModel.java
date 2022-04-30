package com.nvvi9.giphyme.ui.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.nvvi9.giphyme.domain.FavoriteGifsUseCase;
import com.nvvi9.giphyme.vo.FavoriteGifItem;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class FavoriteViewModel extends ViewModel {

    private final FavoriteGifsUseCase favoriteGifsUseCase;

    @Inject
    public FavoriteViewModel(FavoriteGifsUseCase favoriteGifsUseCase) {
        this.favoriteGifsUseCase = favoriteGifsUseCase;
    }

    public Flowable<PagingData<FavoriteGifItem>> getFavoriteGifItems() {
        return favoriteGifsUseCase.getFavorites();
    }

    public Completable deleteFromFavorites(String gifId) {
        return favoriteGifsUseCase.deleteFromFavorites(gifId);
    }
}
