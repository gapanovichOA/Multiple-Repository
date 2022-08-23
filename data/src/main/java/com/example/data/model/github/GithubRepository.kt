package com.example.data.model.github

data class GithubRepository(
    val name: String,
    val owner: GithubUser,
    val description: String
)
