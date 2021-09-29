package com.example.myapp.room

import android.app.Application

class DatabaseApplication: Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

}