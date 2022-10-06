package com.preonboarding.moviereview.di

import com.preonboarding.moviereview.data.remote.repository.MovieRepositoryImpl
import com.preonboarding.moviereview.data.remote.repository.RemoteRepositoryImpl
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
import com.preonboarding.moviereview.presentation.ui.home.source.GetMovieRepository
import com.preonboarding.moviereview.presentation.ui.home.source.GetMovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsRemoteRepository(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository

    @Binds
    @Singleton
    abstract fun bindsGetMovieRepository(
        getMovieRepositoryImpl: GetMovieRepositoryImpl
    ): GetMovieRepository

    @Binds
    @Singleton
    abstract fun bindsMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}