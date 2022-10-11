package com.preonboarding.di

import androidx.databinding.ktx.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.preonboarding.data.common.constant.BASE_URL_KOBIS
import com.preonboarding.data.common.constant.BASE_URL_OMDB
import com.preonboarding.data.remote.api.BoxOfficeService
import com.preonboarding.data.remote.api.MovieInfoService
import com.preonboarding.data.remote.api.PosterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    /*
    * Retrofit
    * */

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class KobisRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class OMDBRetrofit

    @Provides
    @Singleton
    @KobisRetrofit
    fun provideKobisRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_KOBIS)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    @OMDBRetrofit
    fun provideOMDBRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_OMDB)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    /*
    * Api
    * */

    @Provides
    @Singleton
    fun provideBoxOfficeApiService(
        @KobisRetrofit retrofit: Retrofit,
    ): BoxOfficeService {
        return retrofit.create(BoxOfficeService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieInfoApiService(
        @KobisRetrofit retrofit: Retrofit,
    ): MovieInfoService {
        return retrofit.create(MovieInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideOMDBApiService(
        @OMDBRetrofit retrofit: Retrofit,
    ): PosterService {
        return retrofit.create(PosterService::class.java)
    }

    /*
    * HttpClient
    * */

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /*
    * Converter
    * */

    @Provides
    @Singleton
    fun providesConverterFactory() =
        json.asConverterFactory("application/json".toMediaType())

    /*
    * Interceptor
    * */

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }


}