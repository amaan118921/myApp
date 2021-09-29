package com.example.myapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {

    @Insert
    suspend fun insert(data: DataInfo): Long


    @Query("SELECT * FROM DataInfo ORDER BY name ASC")
    fun getUserDetails(): Flow<DataInfo>


}