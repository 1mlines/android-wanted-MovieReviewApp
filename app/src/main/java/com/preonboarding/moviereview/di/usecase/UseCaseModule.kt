package com.preonboarding.moviereview.di.usecase

import com.preonboarding.moviereview.data.usecase.DailyBoxOfficeUseCaseImpl
import com.preonboarding.moviereview.data.usecase.MovieInfosUseCaseImpl
import com.preonboarding.moviereview.data.usecase.PosterInfoUseCaseImpl
import com.preonboarding.moviereview.data.usecase.ReviewListUseCaseImpl
import com.preonboarding.moviereview.domain.usecase.DailyBoxOfficeUseCase
import com.preonboarding.moviereview.domain.usecase.MovieInfosUseCase
import com.preonboarding.moviereview.domain.usecase.PosterInfoUseCase
import com.preonboarding.moviereview.domain.usecase.ReviewListUseCase
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

    @Binds
    @ViewModelScoped
    abstract fun bindPosterInfoUseCase(posterInfoUseCase: PosterInfoUseCaseImpl): PosterInfoUseCase

//    @Binds
//    @ViewModelScoped
//    abstract fun bindReviewsUseCase(reviewsUseCase: ReviewListUseCaseImpl): ReviewListUseCase


}