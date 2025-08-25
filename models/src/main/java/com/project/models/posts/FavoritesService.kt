package com.project.models.posts

interface FavoritesService {
    suspend fun getFavorites(): List<FavoritePost>
    suspend fun addToFavorites(post: Post): Boolean
    suspend fun removeFromFavorites(postId: Int): Boolean
    suspend fun isFavorite(postId: Int): Boolean
}