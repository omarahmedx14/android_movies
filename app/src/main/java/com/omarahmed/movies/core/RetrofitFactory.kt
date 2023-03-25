package com.omarahmed.movies.core

import com.google.gson.GsonBuilder
import com.omarahmed.movies.features.movies.data.api.MoviesApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private const val TIMEOUT: Long = Constants.RETROFIT_TIMEOUT

    fun <T> createMoviesRetrofitFactory(
        anyApiServiceClass: Class<T>,
    ): T {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        return getRetrofitBuilder(okHttpClient).create(anyApiServiceClass)
    }

    private fun getInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request: Request.Builder =
                chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
            chain.proceed(request.build())
        }
    }

    private fun getRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }
}