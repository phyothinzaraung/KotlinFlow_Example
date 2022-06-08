package com.phyothinzaraung.kotlinflow_example.data.local

import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {

    fun getUsers(): Flow<List<User>>

    fun insertAll(users: List<User>): Flow<Unit>
}