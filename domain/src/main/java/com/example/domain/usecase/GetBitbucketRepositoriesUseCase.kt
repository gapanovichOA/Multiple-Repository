package com.example.domain.usecase

import com.example.domain.model.RepositoryModel
import com.example.domain.repository.BitbucketRepository

class GetBitbucketRepositoriesUseCase (private val bitbucketRepository: BitbucketRepository) {

    suspend operator fun invoke(): List<RepositoryModel.BitbucketRepositoryModel> {
        return bitbucketRepository.getBitbucketRepositories()
    }

}