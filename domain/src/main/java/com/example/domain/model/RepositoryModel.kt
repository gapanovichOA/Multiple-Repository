package com.example.domain.model

sealed class RepositoryModel {
    data class BitbucketRepositoryModel(
        val name: String,
        val description: String,
        val userImage: String
    ) : RepositoryModel()

    data class GithubRepositoryModel(
        val name: String,
        val owner: GithubUserModel,
        val description: String
    ) : RepositoryModel()
}
