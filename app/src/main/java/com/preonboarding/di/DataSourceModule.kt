package com.preonboarding.di

import com.preonboarding.data.remote.datasource.MovieRemoteDataSource
import com.preonboarding.data.remote.datasourceimpl.MovieRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl,
    ): MovieRemoteDataSource
}