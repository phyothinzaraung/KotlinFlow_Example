package com.phyothinzaraung.kotlinflow_example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser(): List<User>

    @Insert
    fun insertAllUsers(users: List<User>)

    @Delete
    fun deleteUser(user: User)
}