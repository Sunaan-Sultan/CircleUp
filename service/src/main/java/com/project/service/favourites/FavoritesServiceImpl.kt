package com.project.service.favourites

import android.content.Context
import com.project.models.posts.FavoritePost
import com.project.models.posts.FavoritesRepository
import com.project.models.posts.FavoritesService
import com.project.models.posts.Post
import com.project.repository.favourites.FavoritesLocalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesServiceImpl(
    private val context: Context,
    private val repository: FavoritesRepository = FavoritesLocalRepositoryImpl(context)
) : FavoritesService {

    override suspend fun getFavorites(): List<FavoritePost> =
        withContext(Dispatchers.IO) {
            try {
                repository.getFavorites()
            } catch (e: Exception) {
                throw Exception("Failed to load favorites: ${e.message}")
            }
        }

    override suspend fun addToFavorites(post: Post): Boolean =
        withContext(Dispatchers.IO) {
            try {
                repository.addToFavorites(post)
                true
            } catch (e: Exception) {
                throw Exception("Failed to add to favorites: ${e.message}")
            }
        }

    override suspend fun removeFromFavorites(postId: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                repository.removeFromFavorites(postId)
                true
            } catch (e: Exception) {
                throw Exception("Failed to remove from favorites: ${e.message}")
            }
        }

    override suspend fun isFavorite(postId: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                repository.isFavorite(postId)
            } catch (e: Exception) {
                false
            }
        }
}