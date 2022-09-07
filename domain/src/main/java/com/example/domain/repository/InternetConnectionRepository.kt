package com.example.domain.repository

interface InternetConnectionRepository {
    fun checkInternetConnection(): Boolean
}