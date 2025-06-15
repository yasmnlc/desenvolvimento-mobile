package com.example.postapp.data

import com.example.postapp.data.models.User
import com.example.postapp.data.models.UserCreateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("users/")
    suspend fun createUser(@Body user: UserCreateRequest): User

    @GET("users/")
    suspend fun getUsers(): List<User>
}