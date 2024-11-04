package com.example.absassignment.data.network

import com.example.absassignment.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("api/")
    suspend fun getRandomUsers(@Query("results") results: Int): Response<UserResponse>
}
