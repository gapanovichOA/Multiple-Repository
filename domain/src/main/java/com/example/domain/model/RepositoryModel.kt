package com.example.domain.model

sealed class RepositoryModel(val type: String) {
    data class BitbucketRepositoryModel(
        val name: String,
        val description: String,
        val userImage: String
    ) : RepositoryModel(BITBUCKET)

    data class GithubRepositoryModel(
        val name: String,
        val owner: GithubUserModel,
        val description: String
    ) : RepositoryModel(GITHUB)

    companion object{
        private const val GITHUB = "Github"
        private const val BITBUCKET = "Bitbucket"
    }
}
