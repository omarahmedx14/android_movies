package com.omarahmed.movies.features.movies.domain.di

import com.omarahmed.movies.core.RetrofitFactory
import com.omarahmed.movies.features.movies.data.api.MoviesApiService
import com.omarahmed.movies.features.movies.data.repos.MoviesRepo
import com.omarahmed.movies.features.movies.data.repos.MoviesRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesApiModule {

    @Provides
    @Singleton
    fun provideMoviesApiService(): MoviesApiService {
        return RetrofitFactory.createMoviesRetrofitFactory(MoviesApiService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesRepoModule {

    @Singleton
    @Binds
    abstract fun provideMoviesRepo(moviesRepoImpl: MoviesRepoImpl): MoviesRepo
}