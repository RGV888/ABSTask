package com.example.absassignment.data.repository

import com.example.absassignment.data.model.User
import com.example.absassignment.data.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUsers(number: Int): List<User>? {
        return try {
            val response = apiService.getRandomUsers(number)
            if (response.isSuccessful) {
               return response.body()?.results!!
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}
