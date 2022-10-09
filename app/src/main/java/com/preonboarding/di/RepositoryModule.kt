package com.preonboarding.di

import com.preonboarding.data.repositoryimpl.FirebaseRepositoryImpl
import com.preonboarding.data.repositoryimpl.MovieRepositoryImpl
import com.preonboarding.domain.repository.FirebaseRepository
import com.preonboarding.domain.repository.MovieRepository
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
    abstract fun bindMovieRemoteDataSource(
        movieRepositoryImpl: MovieRepositoryImpl,
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(
        impl: FirebaseRepositoryImpl
    ): FirebaseRepository
}