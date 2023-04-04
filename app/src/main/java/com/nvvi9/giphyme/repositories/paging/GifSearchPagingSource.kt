package com.nvvi9.giphyme.repositories.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.nvvi9.giphyme.data.preview.GifPreview
import com.nvvi9.giphyme.network.retrofit.GiphyApiService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GifSearchPagingSource(
    private val giphyApiService: GiphyApiService,
    private val query: String
) : RxPagingSource<Int, GifPreview>() {

    override fun getRefreshKey(state: PagingState<Int, GifPreview>): Int? =
        state.anchorPosition
            ?.let { state.closestPageToPosition(it) }
            ?.prevKey

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GifPreview>> {
        val page = params.key ?: 0

        return giphyApiService.getSearch(query, PAGE_SIZE, page)
            .subscribeOn(Schedulers.io())
            .map { giphyResponse ->
                LoadResult.Page(
                    giphyResponse.data.map { GifPreview.fromGiphyDataItem(it) },
                    page.takeIf { it != 0 }?.let { it - PAGE_SIZE },
                    giphyResponse.pagination.count.takeIf { it >= PAGE_SIZE }
                        ?.let { page + PAGE_SIZE }
                ) as LoadResult<Int, GifPreview>
            }
            .onErrorReturn { LoadResult.Error(it) }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}