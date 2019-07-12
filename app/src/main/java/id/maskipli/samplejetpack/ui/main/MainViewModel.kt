package id.maskipli.samplejetpack.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.maskipli.samplejetpack.base.BaseViewModel
import id.maskipli.samplejetpack.datasource.ArticleDataSourceFactory
import id.maskipli.samplejetpack.di.network.ApiService
import id.maskipli.samplejetpack.models.RequestState
import kotlinx.coroutines.launch

/**
 * @author hidayat @on 12/07/19
 **/
class MainViewModel(apiService: ApiService) : BaseViewModel() {

    private val articleDataSource = ArticleDataSourceFactory(apiService, coroutineScope = viewModelScope)
    val listArticle = LivePagedListBuilder(articleDataSource, pagedListConfig()).build()
    val requestState: LiveData<RequestState>? = switchMap(articleDataSource.dataSource) { it.requestState }
    val errorMessages: LiveData<String>? = switchMap(articleDataSource.dataSource) { it.errorMessage }

    fun search(query: String) = viewModelScope.launch {
        loading.postValue(true)
        articleDataSource.updateQuery(query)
    }

    private fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(5)
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()
}