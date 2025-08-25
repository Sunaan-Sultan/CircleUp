package com.project.models.posts

interface FavoritesRepository {
    suspend fun addToFavorites(post: Post)
    suspend fun removeFromFavorites(postId: Int)
    suspend fun getFavorites(): List<FavoritePost>
    suspend fun isFavorite(postId: Int): Boolean
    suspend fun getFavoriteIds(): Set<Int>
}