package com.example.postapp.data.models

data class User (
    val id: Int,
    val name: String,
    val email: String
)

data class UserCreateRequest (
    val name: String,
    val email: String
)