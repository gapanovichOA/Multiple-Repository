package com.example.domain.usecase

import com.example.domain.repository.InternetConnectionRepository

class CheckInternetConnectionUseCase(private val internetConnectionRepository: InternetConnectionRepository) {

    operator fun invoke(): Boolean{
        return internetConnectionRepository.checkInternetConnection()
    }
}