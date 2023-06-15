package io.github.c23pr487.lapakin.ui.marketresearch

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.c23pr487.lapakin.model.ArticlesItem
import io.github.c23pr487.lapakin.repository.NewsApiService

class NewsPagingSource(
    val service: NewsApiService,
) : PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = service.getNews(nextPageNumber)
            return LoadResult.Page(
                data = response.articles ?: listOf(),
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}