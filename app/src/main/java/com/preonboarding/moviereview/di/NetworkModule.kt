package com.preonboarding.moviereview.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.preonboarding.moviereview.data.api.KobisMovieApi
import com.preonboarding.moviereview.data.api.OmdbMovieApi
import com.preonboarding.moviereview.presentation.common.const.KOBIS_BASE_URL
import com.preonboarding.moviereview.presentation.common.const.OMDB_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private object OmdbInfo {
//        const val OMDB_BASE_URL = "http://www.omdbapi.com/"
//        const val OMDB_API_KEY = "cfc38158"
//    }
//
//    private object KobisInfo {
//        const val KOBIS_BASE_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json"
//        const val KPBIS_API_KEY = "03d04ad2be0150a76a8293861648773c"
//    }

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun providesHeaderInterceptor() = Interceptor { chain ->
        with(chain) {
            val request = request().newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesLoggerInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun providesConvertorFactory() =
        json.asConverterFactory("application/json".toMediaType())

    // KOBIS RETROFIT
    @Provides
    @Singleton
    fun providesKobisRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(KOBIS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    // OMDB RETROFIT
    @Provides
    @Singleton
    fun providesOMDbRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(OMDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    // KOBIS API
    @Provides
    @Singleton
    fun providesKobisApi(retrofit: Retrofit): KobisMovieApi
            = retrofit.create(KobisMovieApi::class.java)

    // OMDB API
    @Provides
    @Singleton
    fun providesOMDbApi(retrofit: Retrofit): OmdbMovieApi
            = retrofit.create(OmdbMovieApi::class.java)
}