package com.phyothinzaraung.kotlinflow_example.data.api

import com.phyothinzaraung.kotlinflow_example.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("more-users")
    suspend fun getMoreUsers(): List<User>

    @GET("error")
    suspend fun getUserWithError(): List<User>
}