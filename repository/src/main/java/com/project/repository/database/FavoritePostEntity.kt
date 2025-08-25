package com.project.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_posts")
data class FavoritePostEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val createdAt: Long = System.currentTimeMillis()
)