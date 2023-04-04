package com.nvvi9.giphyme.repositories.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.nvvi9.giphyme.data.giphy.gifs.GiphyResponse
import com.nvvi9.giphyme.data.preview.GifPreview
import com.nvvi9.giphyme.data.preview.GifPreviewRemoteKeys
import com.nvvi9.giphyme.db.GifPreviewDataStore
import com.nvvi9.giphyme.db.dao.GifInfoRemoteKeysDao
import com.nvvi9.giphyme.network.retrofit.GiphyApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

@OptIn(ExperimentalPagingApi::class)
class GifPageKeyedRemoteMediator(
    private val giphyApiService: GiphyApiService,
    private val gifPreviewDataStore: GifPreviewDataStore,
    private val gifInfoRemoteKeysDao: GifInfoRemoteKeysDao
) : RxRemoteMediator<Int, GifPreview>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, GifPreview>
    ): Single<MediatorResult> =
        Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .flatMap { type ->
                when (type) {
                    LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state)
                        .map { gifPreviewRemoteKeys -> gifPreviewRemoteKeys.nextKey?.let { it - PAGE_SIZE } ?: 0 }
                        .onErrorReturn { 0 }
                    LoadType.PREPEND -> getRemoteKeyForFirstItem(state)
                        .map { it.prevKey ?: INVALID_PAGE }
                        .onErrorReturn { 0 }
                    LoadType.APPEND -> getRemoteKeyForLastItem(state)
                        .map { it.nextKey ?: INVALID_PAGE }
                    else -> Single.error(IllegalStateException("load type is null"))
                }
            }.flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(true))
                } else {
                    giphyApiService.getTrending(PAGE_SIZE, page, "g")
                        .flatMap { insertIntoDb(page, loadType, it) }
                        .map { MediatorResult.Success(it.pagination.count < PAGE_SIZE) as MediatorResult }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
            .onErrorReturn { MediatorResult.Error(it) }

    private fun insertIntoDb(
        page: Int,
        loadType: LoadType,
        giphyResponse: GiphyResponse
    ): Single<GiphyResponse> = if (loadType == LoadType.REFRESH) {
        gifInfoRemoteKeysDao.clear().andThen(gifPreviewDataStore.clear())
    } else {
        Completable.complete()
    }.andThen(
        gifInfoRemoteKeysDao.insert(
            giphyResponse.data.map { dataItem ->
                GifPreviewRemoteKeys(
                    dataItem.id,
                    page.takeIf { it != 0 }?.let { page - PAGE_SIZE },
                    if (giphyResponse.pagination.count < PAGE_SIZE) null else page + PAGE_SIZE
                )
            })
    )
        .andThen(gifPreviewDataStore.insert(giphyResponse.data.map { GifPreview.fromGiphyDataItem(it) }))
        .toSingleDefault(giphyResponse)

    private fun getRemoteKeyForLastItem(state: PagingState<Int, GifPreview>) =
        Single.just(state.pages).flatMap { pages ->
            pages.takeIf { it.isNotEmpty() }?.let {
                pages[pages.size - 1].data.takeIf { it.isNotEmpty() }
                    ?.let { gifInfoRemoteKeysDao.getRemoteKeys(it[it.size - 1].id) }
            } ?: Single.error(IllegalStateException("Empty pages"))
        }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, GifPreview>) =
        Single.just(state.pages).flatMap { pages ->
            pages.takeIf { it.isNotEmpty() }?.let {
                pages[0].data.takeIf { it.isNotEmpty() }?.let {
                    gifInfoRemoteKeysDao.getRemoteKeys(it[0].id)
                }
            } ?: Single.error(IllegalStateException("Empty pages"))
        }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GifPreview>) =
        Single.just(state).flatMap { pagingState ->
            pagingState.anchorPosition?.let { anchorPosition ->
                pagingState.closestItemToPosition(anchorPosition)?.let {
                    gifInfoRemoteKeysDao.getRemoteKeys(it.id)
                } ?: Single.error(IllegalStateException("gif info is null"))
            } ?: Single.error(IllegalStateException("anchor position is null"))
        }

    companion object {
        private const val INVALID_PAGE = -1
        private const val PAGE_SIZE = 20
    }
}