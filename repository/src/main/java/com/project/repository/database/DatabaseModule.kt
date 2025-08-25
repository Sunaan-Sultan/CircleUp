package com.project.repository.database

import android.content.Context

object DatabaseModule {

    @Volatile
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val instance = AppDatabase.getDatabase(context)
            database = instance
            instance
        }
    }

    fun provideFavoritePostDao(context: Context): FavoritePostDao {
        return provideDatabase(context).favoritePostDao()
    }

    fun provideCachedPostDao(context: Context): CachedPostDao {
        return provideDatabase(context).cachedPostDao()
    }
}