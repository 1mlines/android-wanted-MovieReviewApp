package com.preonboarding.moviereview.di

import com.preonboarding.moviereview.data.source.remote.MovieListDataSource
import com.preonboarding.moviereview.data.source.remote.MovieListDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    @Singleton
    abstract fun bindsMovieListDataSource(
        movieListDataSourceImpl: MovieListDataSourceImpl
    ): MovieListDataSource
}