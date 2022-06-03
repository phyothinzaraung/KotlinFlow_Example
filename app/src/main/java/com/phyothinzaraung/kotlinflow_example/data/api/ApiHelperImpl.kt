package com.phyothinzaraung.kotlinflow_example.data.api

import com.phyothinzaraung.kotlinflow_example.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override fun getUsers(): Flow<List<User>> = flow { emit(apiService.getUsers()) }

    override fun getMoreUsers(): Flow<List<User>> = flow { emit(apiService.getMoreUsers()) }

    override fun getUsersWithError(): Flow<List<User>> = flow { emit(apiService.getUserWithError()) }
}