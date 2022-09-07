package com.example.data

enum class Error(val message: String)  {
        CLIENT_ERROR("Client's error"),
        SERVER_ERROR ("Server's error"),
        UNEXPECTED_ERROR ("Unexpected error"),
        NULL_BODY ("Null body")
}
