package com.example.domain.usecase

import com.example.domain.model.GithubRepositoryModel
import com.example.domain.repository.GithubRepository

class GetGithubRepositoriesUseCase(private val githubRepository: GithubRepository) {

    suspend operator fun invoke(): List<GithubRepositoryModel> {
        return githubRepository.getGithubRepositories()
    }

}