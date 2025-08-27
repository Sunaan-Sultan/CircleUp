package com.project.repository.favourites

import com.project.models.posts.FavoritePost
import com.project.models.posts.FavouritesRepository
import com.project.models.posts.Post

/**
 * This class is for live repository implementation
 */

class FavouritesRepositoryImpl : FavouritesRepository {
    override suspend fun addToFavorites(post: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorites(postId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): List<FavoritePost> {
        TODO("Not yet implemented")
    }

    override suspend fun isFavorite(postId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteIds(): Set<Int> {
        TODO("Not yet implemented")
    }

}