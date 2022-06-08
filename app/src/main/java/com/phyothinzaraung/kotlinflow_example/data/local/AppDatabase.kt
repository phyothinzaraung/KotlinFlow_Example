package com.phyothinzaraung.kotlinflow_example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phyothinzaraung.kotlinflow_example.data.local.dao.UserDao
import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}