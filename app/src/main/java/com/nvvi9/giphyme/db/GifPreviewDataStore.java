package com.nvvi9.giphyme.db;

import androidx.paging.PagingSource;

import com.nvvi9.giphyme.data.gifinfo.GifInfo;
import com.nvvi9.giphyme.data.preview.GifPreview;
import com.nvvi9.giphyme.db.dao.GifInfoDao;
import com.nvvi9.giphyme.db.dao.GifPreviewDao;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class GifPreviewDataStore {

    private final GifPreviewDao gifPreviewDao;
    private final GifInfoDao gifInfoDao;

    @Inject
    public GifPreviewDataStore(GifPreviewDao gifPreviewDao, GifInfoDao gifInfoDao) {
        this.gifPreviewDao = gifPreviewDao;
        this.gifInfoDao = gifInfoDao;
    }

    public Completable insert(List<GifPreview> gifPreviews) {
        List<String> previewIds = gifPreviews.stream().map(GifPreview::getId).collect(Collectors.toList());
        return gifInfoDao.getGifs(previewIds)
                .map(gifInfoList -> gifInfoList.stream().map(GifInfo::getId).collect(Collectors.toList()))
                .map(gifInfoIds -> gifPreviews.stream()
                        .map(gifPreview -> gifInfoIds.contains(gifPreview.getId())
                                ? new GifPreview(gifPreview.getId(), gifPreview.getTitle(), gifPreview.getPreviewUrl(), gifPreview.getPreviewUrl(), gifPreview.getHeight(), gifPreview.getWidth(), gifPreview.getSize(), true)
                                : gifPreview).collect(Collectors.toList()))
                .flatMapCompletable(gifPreviewDao::insert);
    }

    public Completable clear() {
        return gifPreviewDao.clear();
    }

    public PagingSource<Integer, GifPreview> getGifs() {
        return gifPreviewDao.getGifs();
    }

    public Completable markAsFavorite(String gifId, Boolean inFavorites) {
        return gifPreviewDao.updateSetInFavourites(gifId, inFavorites);
    }
}
