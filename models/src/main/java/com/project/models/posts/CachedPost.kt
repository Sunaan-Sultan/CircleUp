package com.project.models.posts

data class CachedPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis(),
    val page: Int = 0
)
