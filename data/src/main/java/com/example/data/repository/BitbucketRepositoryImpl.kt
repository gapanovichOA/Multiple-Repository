package com.example.data.repository

import com.example.data.Error
import com.example.data.service.BitbucketApi
import com.example.data.toDomainModel
import com.example.domain.model.RepositoryModel
import com.example.domain.repository.BitbucketRepository
import java.lang.Exception

class BitbucketRepositoryImpl(private val bitbucketApi: BitbucketApi): BitbucketRepository {

    override suspend fun getBitbucketRepositories(): List<RepositoryModel.BitbucketRepositoryModel> {
        val response = bitbucketApi.getRepositories(FIELDS)
        return if (response.isSuccessful) {
            val repositoriesList = response.body()?.values ?: emptyList()
            repositoriesList.map{ bitbucketRepository ->
                bitbucketRepository.toDomainModel()
            }
        } else {
            when (response.code()) {
                in 400..499 -> throw Exception(Error.CLIENT_ERROR.message)
                in 500..599 -> throw Exception(Error.SERVER_ERROR.message)
                else -> throw Exception(Error.UNEXPECTED_ERROR.message)
            }
        }
    }

    companion object{
        private const val FIELDS ="values.name,values.owner,values.description"
    }
}