package com.preonboarding.moviereview.di

import com.preonboarding.moviereview.data.local.repository.gallery.GalleryRepositoryImpl
import com.preonboarding.moviereview.data.remote.repository.MovieRepositoryImpl
import com.preonboarding.moviereview.data.remote.repository.RemoteRepositoryImpl
import com.preonboarding.moviereview.domain.repository.local.gallery.GalleryRepository
import com.preonboarding.moviereview.domain.repository.remote.MovieRepository
import com.preonboarding.moviereview.domain.repository.remote.RemoteRepository
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
    abstract fun bindsMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindsGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository
}