package com.nvvi9.giphyme.ui.viewmodels;

import androidx.lifecycle.ViewModel;

import com.nvvi9.giphyme.domain.GifDetailsUseCase;
import com.nvvi9.giphyme.vo.GifDetailsItem;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class GifDetailsViewModel extends ViewModel {

    private final GifDetailsUseCase gifDetailsUseCase;

    @Inject
    public GifDetailsViewModel(GifDetailsUseCase gifDetailsUseCase) {
        this.gifDetailsUseCase = gifDetailsUseCase;
    }

    public Observable<GifDetailsItem> getGifDetails(String gifId) {
        return gifDetailsUseCase.getGifDetails(gifId);
    }
}
