package com.example.data.model.github

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String,
    val avatar_url: String
)
