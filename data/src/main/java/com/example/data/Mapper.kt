package com.example.data

import com.example.data.model.bitbucket.BitbucketRepository
import com.example.data.model.github.GithubRepository
import com.example.data.model.github.GithubUser
import com.example.domain.model.GithubUserModel
import com.example.domain.model.RepositoryModel

fun GithubRepository.toDomainModel() =
    RepositoryModel.GithubRepositoryModel(name, owner.toDomainModel(), description)

fun GithubUser.toDomainModel() = GithubUserModel(login, avatar_url)

fun BitbucketRepository.toDomainModel() =
    RepositoryModel.BitbucketRepositoryModel(name,description,owner.links.avatar.href)

