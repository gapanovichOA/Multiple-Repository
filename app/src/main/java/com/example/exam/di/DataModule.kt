package com.example.exam.di

import com.example.data.repository.GithubRepositoryImpl
import com.example.data.service.GithubApi
import com.example.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideLastFMRepository(
        githubApi: GithubApi
    ): GithubRepository {
        return GithubRepositoryImpl(githubApi)
    }
}