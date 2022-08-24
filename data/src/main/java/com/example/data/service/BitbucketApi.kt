package com.example.data.service

import com.example.data.model.bitbucket.ListBitbucketRepositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitbucketApi {
    @GET("repositories")
    suspend fun getRepositories(
        @Query("fields") fields: String
    ): Response<ListBitbucketRepositories>
}