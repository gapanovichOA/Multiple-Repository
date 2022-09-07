package com.example.data.repository

import com.example.data.Error
import com.example.data.service.GithubApi
import com.example.data.toDomainModel
import com.example.domain.model.RepositoryModel
import com.example.domain.repository.GithubRepository
import java.lang.Exception

class GithubRepositoryImpl(private val githubApi: GithubApi): GithubRepository {

    override suspend fun getGithubRepositories(): List<RepositoryModel.GithubRepositoryModel> {
        val response = githubApi.getRepositories()
        return if (response.isSuccessful) {
            var repositoriesList = response.body() ?: emptyList()
            repositoriesList = repositoriesList.filter {
                it.description != null
            }
            repositoriesList.map{ githubRepository ->
                githubRepository.toDomainModel()
            }
        } else {
            when (response.code()) {
                in 400..499 -> throw Exception(Error.CLIENT_ERROR.message)
                in 500..599 -> throw Exception(Error.SERVER_ERROR.message)
                else -> throw Exception(Error.UNEXPECTED_ERROR.message)
            }
        }
    }
}