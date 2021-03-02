package com.example.skycatnews.model.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class SkyCatNewsModule {
    companion object {
        const val DEFAULT_API_URL = "https://5b76e458-8714-4eba-9acd-032272e82913.mock.pstmn.io"
        const val TIMEOUT: Long = 3000 // ms
    }

    @Singleton
    @Provides
    open fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

    }


    @Singleton
    @Provides
    open fun provideRetrofit(
            okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
                .baseUrl(DEFAULT_API_URL)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .client(okHttpClient)
                .build()
    }

}