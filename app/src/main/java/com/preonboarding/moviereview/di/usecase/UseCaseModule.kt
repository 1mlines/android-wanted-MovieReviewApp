package com.preonboarding.moviereview.di.usecase

import com.preonboarding.moviereview.data.usecase.DailyBoxOfficeUseCaseImpl
import com.preonboarding.moviereview.data.usecase.MovieInfosUseCaseImpl
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindDailyBoxOfficeUseCase(dailyBoxOfficeUseCase: DailyBoxOfficeUseCaseImpl): DailyBoxOfficeUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindMovieInfosUseCase(movieInfosUseCase: MovieInfosUseCaseImpl): MovieInfosUseCase
}