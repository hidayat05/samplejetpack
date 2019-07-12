package id.maskipli.samplejetpack.di.network

import id.maskipli.samplejetpack.Apps
import id.maskipli.samplejetpack.R
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author hidayat @on 12/07/19
 **/
class RequestInterceptor : Interceptor {

    companion object {
        private const val CLIENT_KEY_PARAMS = "apiKey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val customUrl = originalUrl.newBuilder()
            .addQueryParameter(CLIENT_KEY_PARAMS, Apps.applicationContext().getString(R.string.api_key))
            .build()

        val requestBuilder = originalRequest.newBuilder()
            .url(customUrl)

        return chain.proceed(requestBuilder.build())
    }
}