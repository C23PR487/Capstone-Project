package io.github.c23pr487.lapakin.ui.marketresearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.c23pr487.lapakin.repository.ApiConfig

class MarketResearchViewModel : ViewModel() {

    private val service = ApiConfig.getNewsApiService()

    val flow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        NewsPagingSource(service)
    }.flow.cachedIn(viewModelScope)
}