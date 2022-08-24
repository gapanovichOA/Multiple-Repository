package com.example.data.repository

import com.example.data.service.BitbucketApi
import com.example.data.toDomainModel
import com.example.domain.model.RepositoryModel
import com.example.domain.repository.BitbucketRepository
import java.lang.Exception

class BitbucketRepositoryImpl(private val bitbucketApi: BitbucketApi): BitbucketRepository {

    override suspend fun getBitbucketRepositories(): List<RepositoryModel.BitbucketRepositoryModel> {
        val response = bitbucketApi.getRepositories(FIELDS)
        return if (response.isSuccessful) {
            val repositoriesList = response.body()?.values ?: throw Exception(NULL_BODY)
            repositoriesList.map{ bitbucketRepository ->
                bitbucketRepository.toDomainModel()
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
        private const val FIELDS ="values.name,values.owner,values.description"
    }
}