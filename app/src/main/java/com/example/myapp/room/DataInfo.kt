package com.example.myapp.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DataInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name")@NonNull val name: String,
    @ColumnInfo(name="email") @NonNull val email: String,
    @ColumnInfo(name="userToken")@NonNull val userToken: String,
    @ColumnInfo(name = "uri")@NonNull val uri: String,
    @ColumnInfo(name = "phone")@NonNull val phone: String

)


