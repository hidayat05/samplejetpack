package id.maskipli.samplejetpack.models.response

import id.maskipli.samplejetpack.models.Article

/**
 * @author hidayat @on 12/07/19
 **/
data class ArticlesResponse(
        val status: String,
        val totalResults: Int,
        val articles: List<Article>
)