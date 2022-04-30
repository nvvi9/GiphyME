package com.nvvi9.giphyme.repositories.paging;

import androidx.annotation.NonNull;
import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxRemoteMediator;

import com.nvvi9.giphyme.data.giphy.gifs.GiphyResponse;
import com.nvvi9.giphyme.data.preview.GifPreview;
import com.nvvi9.giphyme.data.preview.GifPreviewRemoteKeys;
import com.nvvi9.giphyme.db.GifPreviewDataStore;
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao;
import com.nvvi9.giphyme.network.retrofit.GiphyApiService;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GifPageKeyedRemoteMediator extends RxRemoteMediator<Integer, GifPreview> {

    private final GiphyApiService giphyApiService;
    private final GifPreviewDataStore gifPreviewDataStore;
    private final GifInfoRemoteKeysDao gifInfoRemoteKeysDao;

    private final static Integer INVALID_PAGE = -1;
    private final static Integer PAGE_SIZE = 20;

    @Inject
    public GifPageKeyedRemoteMediator(GiphyApiService giphyApiService, GifPreviewDataStore gifPreviewDataStore, GifInfoRemoteKeysDao gifInfoRemoteKeysDao) {
        this.giphyApiService = giphyApiService;
        this.gifPreviewDataStore = gifPreviewDataStore;
        this.gifInfoRemoteKeysDao = gifInfoRemoteKeysDao;
    }

    @NonNull
    @Override
    public Single<MediatorResult> loadSingle(@NonNull LoadType loadType, @NonNull PagingState<Integer, GifPreview> pagingState) {
        return Single.just(loadType)
                .subscribeOn(Schedulers.io())
                .flatMap(type -> {
                    switch (type) {
                        case REFRESH:
                            return getRemoteKeyClosestToCurrentPosition(pagingState)
                                    .map(gifPreviewRemoteKeys -> gifPreviewRemoteKeys.getNextKey() != null ? gifPreviewRemoteKeys.getNextKey() - PAGE_SIZE : 0)
                                    .onErrorReturn(throwable -> 0);
                        case PREPEND:
                            return getRemoteKeyForFirstItem(pagingState)
                                    .map(gifPreviewRemoteKeys -> gifPreviewRemoteKeys.getPrevKey() != null ? gifPreviewRemoteKeys.getPrevKey() : INVALID_PAGE)
                                    .onErrorReturn(throwable -> 0);
                        case APPEND:
                            return getRemoteKeyForLastItem(pagingState)
                                    .map(gifPreviewRemoteKeys -> gifPreviewRemoteKeys.getNextKey() != null ? gifPreviewRemoteKeys.getNextKey() : INVALID_PAGE);
                        default:
                            return Single.error(new IllegalStateException("load type is null"));
                    }
                })
                .flatMap(page -> {
                    if (page.equals(INVALID_PAGE)) {
                        return Single.just(new MediatorResult.Success(true));
                    } else {
                        return giphyApiService.getTrending(PAGE_SIZE, page, "g")
                                .flatMap(giphyResponse -> insertIntoDb(page, loadType, giphyResponse))
                                .map(giphyResponse -> (MediatorResult) new MediatorResult.Success(giphyResponse.getPagination().getCount() < PAGE_SIZE))
                                .onErrorReturn(MediatorResult.Error::new);
                    }
                })
                .onErrorReturn(MediatorResult.Error::new);
    }

    private Single<GiphyResponse> insertIntoDb(Integer page, LoadType loadType, GiphyResponse giphyResponse) {
        Integer prevKey = page != 0 ? page - PAGE_SIZE : null;
        Integer nextKey = giphyResponse.getPagination().getCount() < PAGE_SIZE ? null : page + PAGE_SIZE;

        List<GifPreviewRemoteKeys> remoteKeys = giphyResponse.getData().stream()
                .map(dataItem -> new GifPreviewRemoteKeys(dataItem.getId(), prevKey, nextKey))
                .collect(Collectors.toList());

        List<GifPreview> gifPreviews = giphyResponse.getData().stream()
                .map(GifPreview::fromGiphyDataItem)
                .collect(Collectors.toList());

        return (loadType == LoadType.REFRESH ? gifInfoRemoteKeysDao.clear().andThen(gifPreviewDataStore.clear()) : Completable.complete())
                .andThen(gifInfoRemoteKeysDao.insert(remoteKeys))
                .andThen(gifPreviewDataStore.insert(gifPreviews))
                .toSingleDefault(giphyResponse);
    }

    private Single<GifPreviewRemoteKeys> getRemoteKeyForLastItem(PagingState<Integer, GifPreview> state) {
        return Single.just(state.getPages())
                .flatMap(pages -> {
                    if (pages.isEmpty()) {
                        return Single.error(new IllegalStateException("Empty pages"));
                    }

                    List<GifPreview> gifPreviews = pages.get(pages.size() - 1).getData();

                    if (gifPreviews.isEmpty()) {
                        return Single.error(new IllegalStateException("Empty page"));
                    }
                    return gifInfoRemoteKeysDao.getRemoteKeys(gifPreviews.get(gifPreviews.size() - 1).getId());
                });
    }

    private Single<GifPreviewRemoteKeys> getRemoteKeyForFirstItem(PagingState<Integer, GifPreview> state) {
        return Single.just(state.getPages()).flatMap(pages -> {
            if (pages.isEmpty()) {
                return Single.error(new IllegalStateException("Empty pages"));
            }

            List<GifPreview> gifPreviews = pages.get(0).getData();

            if (gifPreviews.isEmpty()) {
                return Single.error(new IllegalStateException("Empty page"));
            }

            return gifInfoRemoteKeysDao.getRemoteKeys(gifPreviews.get(0).getId());
        });
    }

    private Single<GifPreviewRemoteKeys> getRemoteKeyClosestToCurrentPosition(PagingState<Integer, GifPreview> state) {
        return Single.just(state).flatMap(pagingState -> {
            Integer anchorPosition = pagingState.getAnchorPosition();
            if (anchorPosition == null) {
                return Single.error(new IllegalStateException("anchor position is null"));
            }

            GifPreview gifPreview = pagingState.closestItemToPosition(anchorPosition);

            if (gifPreview == null) {
                return Single.error(new IllegalStateException("gif info is null"));
            }

            return gifInfoRemoteKeysDao.getRemoteKeys(gifPreview.getId());
        });
    }
}
