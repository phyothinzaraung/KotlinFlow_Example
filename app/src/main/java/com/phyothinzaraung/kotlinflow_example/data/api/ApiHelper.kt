package com.phyothinzaraung.kotlinflow_example.data.api

import com.phyothinzaraung.kotlinflow_example.data.model.User
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getUsers(): Flow<List<User>>

    fun getMoreUsers(): Flow<List<User>>

    fun getUsersWithError(): Flow<List<User>>
}