package com.example.data.model.bitbucket

data class BitbucketRepository(
    val name: String,
    val description: String,
    val owner: BitbucketUser
)