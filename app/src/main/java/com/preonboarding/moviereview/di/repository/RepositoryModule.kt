package com.preonboarding.moviereview.di.repository

import com.preonboarding.moviereview.data.repository.RemoteRepositoryImpl
import com.preonboarding.moviereview.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindRemoteRepository(remoteRepository: RemoteRepositoryImpl): RemoteRepository
}