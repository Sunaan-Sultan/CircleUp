package com.project.example

import android.app.Application
import com.project.repository.database.AppDatabase

class MyApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @Volatile
        private var instance: MyApplication? = null

        fun getInstance(): MyApplication {
            return instance ?: synchronized(this) {
                instance ?: throw IllegalStateException("Application not initialized")
            }
        }
    }
}