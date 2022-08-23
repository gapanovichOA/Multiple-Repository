package com.example.data.service

import com.example.data.model.github.GithubRepository
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {
    @GET("repositories")
    suspend fun getRepositories(): Response<List<GithubRepository>>
}