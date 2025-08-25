package com.project.models.posts

data class FavoritePost(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val createdAt: Long = System.currentTimeMillis()
)
