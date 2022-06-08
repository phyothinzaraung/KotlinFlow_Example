package com.phyothinzaraung.kotlinflow_example.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase{
        if(INSTANCE == null){
            synchronized(AppDatabase::class){
                INSTANCE = buildRoomDatabase(context)
            }

        }
        return INSTANCE!!
    }

    fun buildRoomDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "kotlin-flow-example"
        ).build()

}