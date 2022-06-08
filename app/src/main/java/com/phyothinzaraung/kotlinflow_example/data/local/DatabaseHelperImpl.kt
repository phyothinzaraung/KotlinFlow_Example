package com.phyothinzaraung.kotlinflow_example.data.local

import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseHelperImpl(private val appDatabase: AppDatabase): DatabaseHelper {
    override fun getUsers(): Flow<List<User>> = flow {
        emit(appDatabase.userDao().getAllUser())
    }


    override fun insertAll(users: List<User>): Flow<Unit>  = flow {
        appDatabase.userDao().insertAllUsers(users)
        emit(Unit)
    }
}