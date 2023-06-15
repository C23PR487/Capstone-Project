package io.github.c23pr487.lapakin.ui.marketresearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.c23pr487.lapakin.repository.ApiConfig
import io.github.c23pr487.lapakin.repository.NewsApiService

class MarketResearchViewModel : ViewModel() {

    private val service = ApiConfig.getNewsApiService()

    val flow = Pager(
        PagingConfig(pageSize=20)
    ) {
        NewsPagingSource(service)
    }.flow.cachedIn(viewModelScope)
}