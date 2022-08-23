package com.example.domain.repository

import com.example.domain.model.GithubRepositoryModel

interface GithubRepository {

    suspend fun getGithubRepositories(): List<GithubRepositoryModel>

}