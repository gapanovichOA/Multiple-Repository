package com.example.domain.model

data class GithubRepositoryModel(
    val name: String,
    val owner: GithubUserModel,
    val description: String
)
