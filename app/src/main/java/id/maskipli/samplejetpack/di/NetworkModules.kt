package id.maskipli.samplejetpack.di

import com.google.gson.GsonBuilder
import id.maskipli.samplejetpack.di.network.ApiService
import id.maskipli.samplejetpack.di.network.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author hidayat @on 12/07/19
 **/
object NetworkModules {

    private const val TIME_OUT = 10L

    val module = module {
        single { createOkHttpClient() }
        single { createApiService(get()) }
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    private fun createApiService(okHttpClient: OkHttpClient): ApiService {
        val gsonBuilder = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}