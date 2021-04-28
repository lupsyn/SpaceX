package com.challenge.data_api.di

import android.content.Context
import com.challenge.data_api.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val RETROFIT_TIMEOUT = 60L

    fun getRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory = getGsonConverterFactory(getGson()),
        baseUrl: String = getBaseUrl()
    ): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)

    fun getHttpBuilder(application: Context) =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }

            readTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
            cache(getOkHttpCache(application))
        }


    private fun getOkHttpCache(application: Context): Cache {
        val cacheDirectory = File(application.cacheDir, HTTP_CACHE_DIR)
        return Cache(cacheDirectory, HTTP_CACHE_SIZE_IN_BYTES)

    }

    private fun getBaseUrl(baseUrlOverride: String? = null) =
        baseUrlOverride ?: "https://api.spacexdata.com/v3/"

    private fun getGson(): Gson = GsonBuilder().create()

    private fun getGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    private const val KILOBYTE_IN_BYTES = 1024
    private const val MEGABYTE_IN_BYTES = 1024 * KILOBYTE_IN_BYTES
    private const val HTTP_CACHE_DIR = "httpcachedir"
    private const val HTTP_CACHE_SIZE_IN_BYTES = 10L * MEGABYTE_IN_BYTES
}
