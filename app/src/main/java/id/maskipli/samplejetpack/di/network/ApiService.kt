package id.maskipli.samplejetpack.di.network

import id.maskipli.samplejetpack.models.response.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author hidayat @on 12/07/19
 **/
interface ApiService {

    @GET("everything")
    fun getAllArticle(
            @Query("q") searchQuery: String,
            @Query("page") page: Long,
            @Query("pageSize") pageSize: Int
    ): Call<ArticlesResponse>

}