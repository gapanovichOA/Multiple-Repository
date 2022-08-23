package com.example.data

import com.example.data.model.github.GithubRepository
import com.example.data.model.github.GithubUser
import com.example.domain.model.GithubRepositoryModel
import com.example.domain.model.GithubUserModel

fun GithubRepository.toDomainModel() = GithubRepositoryModel(name, owner.toDomainModel(), description)

fun GithubUser.toDomainModel() = GithubUserModel(login, avatar_url)

