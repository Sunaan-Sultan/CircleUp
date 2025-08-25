package com.project.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_posts")
data class CachedPostEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis(),
    val page: Int = 0
)
