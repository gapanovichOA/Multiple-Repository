package com.example.exam.di

import android.content.Context
import com.example.data.repository.BitbucketRepositoryImpl
import com.example.data.repository.GithubRepositoryImpl
import com.example.data.repository.InternetConnectionRepositoryImpl
import com.example.data.service.BitbucketApi
import com.example.data.service.GithubApi
import com.example.domain.repository.BitbucketRepository
import com.example.domain.repository.GithubRepository
import com.example.domain.repository.InternetConnectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Provides
    @Singleton
    fun provideBitbucketRepository(
        bitbucketApi: BitbucketApi
    ): BitbucketRepository {
        return BitbucketRepositoryImpl(bitbucketApi)
    }
    @Provides
    @Singleton
    fun provideInternetConnectionRepository(
        @ApplicationContext context: Context
    ): InternetConnectionRepository {
        return InternetConnectionRepositoryImpl(context)
    }
}