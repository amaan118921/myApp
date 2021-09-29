package com.example.myapp.room

import android.content.ClipData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [DataInfo::class], version = 2, exportSchema = false)

abstract class AppDatabase: RoomDatabase() {

   abstract fun itemDao(): Dao

   companion object {

       var INSTANCE : AppDatabase? = null
       fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "item_database").allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
       }

   }

}