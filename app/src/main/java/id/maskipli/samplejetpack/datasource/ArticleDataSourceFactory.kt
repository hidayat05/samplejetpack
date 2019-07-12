package id.maskipli.samplejetpack.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.maskipli.samplejetpack.di.network.ApiService
import id.maskipli.samplejetpack.models.Article
import kotlinx.coroutines.CoroutineScope

/**
 * @author hidayat @on 12/07/19
 **/
class ArticleDataSourceFactory(private val apiService: ApiService,
                               private var query: String = "",
                               private val coroutineScope: CoroutineScope)
    : DataSource.Factory<Long, Article>() {

    val dataSource = MutableLiveData<ArticleDataSource>()

    override fun create(): DataSource<Long, Article> {
        val source = ArticleDataSource(coroutineScope, apiService, query)
        dataSource.postValue(source)
        return source
    }

    fun updateQuery(query: String) {
        this.query = query
        dataSource.value?.refresh()
    }
}