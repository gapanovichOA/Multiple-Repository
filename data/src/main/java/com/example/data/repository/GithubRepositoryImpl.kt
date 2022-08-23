package com.example.data.repository

import com.example.data.service.GithubApi
import com.example.data.toDomainModel
import com.example.domain.model.GithubRepositoryModel
import com.example.domain.repository.GithubRepository
import java.lang.Exception

class GithubRepositoryImpl(private val githubApi: GithubApi): GithubRepository {

    override suspend fun getGithubRepositories(): List<GithubRepositoryModel> {
        val response = githubApi.getRepositories()
        return if (response.isSuccessful) {
            var repositoriesList = response.body() ?: throw Exception(NULL_BODY)
            repositoriesList = repositoriesList.filter {
                it.description != null
            }
            repositoriesList.map{ githubRepository ->
                githubRepository.toDomainModel()
            }
        } else {
            when (response.code()) {
                in 400..499 -> throw Exception(CLIENT_ERROR)
                in 500..599 -> throw Exception(SERVER_ERROR)
                else -> throw Exception(UNEXPECTED_ERROR)
            }
        }
    }

    companion object{
        private const val CLIENT_ERROR = "Client's error"
        private const val SERVER_ERROR = "Server's error"
        private const val UNEXPECTED_ERROR = "Unexpected error"
        private const val NULL_BODY = "Null body"
    }
}