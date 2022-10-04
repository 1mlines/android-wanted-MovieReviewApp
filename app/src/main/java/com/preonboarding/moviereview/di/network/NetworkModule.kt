package com.preonboarding.moviereview.di.network

import com.preonboarding.moviereview.data.network.KobisMovieApi
import com.preonboarding.moviereview.data.network.OmdbMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val kobis_base_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/"
    private const val omdb_base_url = "http://www.omdbapi.com/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @KobisQualifier
    fun provideKobisRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(kobis_base_url)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @OmdbQualifier
    fun provideOmdbRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(omdb_base_url)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @KobisQualifier
    fun provideKobisMovieApi(retrofit: Retrofit): KobisMovieApi {
        return retrofit.create(KobisMovieApi::class.java)
    }

    @Singleton
    @Provides
    @OmdbQualifier
    fun provideOmdbMovieApi(retrofit: Retrofit): OmdbMovieApi {
        return retrofit.create(OmdbMovieApi::class.java)
    }
}

