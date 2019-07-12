package id.maskipli.samplejetpack.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.maskipli.samplejetpack.di.network.ApiService
import id.maskipli.samplejetpack.models.Article
import id.maskipli.samplejetpack.models.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult

/**
 * @author hidayat @on 12/07/19
 **/
class ArticleDataSource(private val coroutineScope: CoroutineScope,
                        private val apiService: ApiService,
                        private val query: String)
    : PageKeyedDataSource<Long, Article>() {

    val requestState = MutableLiveData<RequestState>()
    val errorMessage = MutableLiveData<String>()
    private var retryQuery: (() -> Any)? = null

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Article>) {}

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Article>) {
        retryQuery = { loadInitial(params, callback) }
        executeRequest(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Article>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeRequest(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    fun refresh() = this.invalidate()

    private fun executeRequest(page: Long,
                               pageSize: Int,
                               callback: (List<Article>) -> Unit
    ) = coroutineScope.launch {
        requestState.postValue(RequestState.RUNNING)
        when (val result = apiService.getAllArticle(query, page, pageSize).awaitResult()) {
            is Result.Ok -> {
                callback(result.value.articles)
                requestState.postValue(RequestState.SUCCESS)
            }
            is Result.Error -> {
                errorMessage.postValue(result.exception.message())
                requestState.postValue(RequestState.FAILED)
            }
            is Result.Exception -> {
                errorMessage.postValue(result.exception.message)
                requestState.postValue(RequestState.FAILED)
            }
        }
    }
}