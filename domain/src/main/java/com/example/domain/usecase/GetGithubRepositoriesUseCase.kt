package com.example.domain.usecase

import com.example.domain.model.RepositoryModel
import com.example.domain.repository.GithubRepository

class GetGithubRepositoriesUseCase(private val githubRepository: GithubRepository) {

    suspend operator fun invoke(): List<RepositoryModel.GithubRepositoryModel> {
        return githubRepository.getGithubRepositories()
    }

}