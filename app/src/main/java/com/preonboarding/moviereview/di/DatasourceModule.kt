package com.preonboarding.moviereview.di

import com.preonboarding.moviereview.data.local.source.gallery.GalleryDataSource
import com.preonboarding.moviereview.data.local.source.gallery.GalleryDataSourceImpl
import com.preonboarding.moviereview.data.remote.source.MovieDataSource
import com.preonboarding.moviereview.data.remote.source.MovieDataSourceImpl
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
        movieDataSourceImpl: MovieDataSourceImpl
    ): MovieDataSource

    @Binds
    @Singleton
    abstract fun bindsGalleryImageDataSource(
        galleryImageDataSourceImpl: GalleryDataSourceImpl
    ): GalleryDataSource
}