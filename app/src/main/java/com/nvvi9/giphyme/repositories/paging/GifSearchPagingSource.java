package com.nvvi9.giphyme.repositories.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.nvvi9.giphyme.data.preview.GifPreview;
import com.nvvi9.giphyme.network.retrofit.GiphyApiService;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GifSearchPagingSource extends RxPagingSource<Integer, GifPreview> {

    private final GiphyApiService giphyApiService;
    private final String query;

    private final static int PAGE_SIZE = 20;

    public GifSearchPagingSource(GiphyApiService giphyApiService, String query) {
        this.giphyApiService = giphyApiService;
        this.query = query;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, GifPreview>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        Integer loadKey = loadParams.getKey();
        int page = loadKey != null ? loadKey : 0;

        return giphyApiService.getSearch(query, PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .map(giphyResponse -> {
                    List<GifPreview> gifPreviews = giphyResponse.getData().stream().map(GifPreview::fromGiphyDataItem).collect(Collectors.toList());
                    return (LoadResult<Integer, GifPreview>) new LoadResult.Page<>(
                            gifPreviews,
                            page == 0 ? null : page - PAGE_SIZE,
                            giphyResponse.getPagination().getCount() < PAGE_SIZE ? null : page + PAGE_SIZE
                    );
                })
                .onErrorReturn(LoadResult.Error::new);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, GifPreview> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();

        if (anchorPosition == null) return null;

        LoadResult.Page<Integer, GifPreview> page = pagingState.closestPageToPosition(anchorPosition);
        return page != null ? page.getPrevKey() : null;
    }
}
