package com.example.exam.di

import com.example.domain.repository.BitbucketRepository
import com.example.domain.repository.GithubRepository
import com.example.domain.usecase.GetBitbucketRepositoriesUseCase
import com.example.domain.usecase.GetGithubRepositoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetGithubRepositoriesUseCase(githubRepository: GithubRepository): GetGithubRepositoriesUseCase {
        return GetGithubRepositoriesUseCase(githubRepository)
    }
    @Provides
    fun provideGetBitbucketRepositoriesUseCase(bitbucketRepository: BitbucketRepository): GetBitbucketRepositoriesUseCase {
        return GetBitbucketRepositoriesUseCase(bitbucketRepository)
    }
}