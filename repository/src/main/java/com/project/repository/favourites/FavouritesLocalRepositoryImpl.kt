package com.project.repository.favourites

import android.content.Context
import com.project.models.posts.FavoritePost
import com.project.models.posts.FavouritesRepository
import com.project.models.posts.Post
import com.project.repository.database.DatabaseModule
import com.project.repository.database.FavoritePostDao
import com.project.repository.database.FavoritePostEntity

/**
 * This class is for local repository implementation
 */

class FavouritesLocalRepositoryImpl(
    private val context: Context
) : FavouritesRepository {

    private val favoritePostDao: FavoritePostDao by lazy {
        DatabaseModule.provideFavoritePostDao(context)
    }

    override suspend fun addToFavorites(post: Post) {
        val favoriteEntity = FavoritePostEntity(
            id = post.id,
            userId = post.userId,
            title = post.title,
            body = post.body,
            createdAt = System.currentTimeMillis()
        )
        favoritePostDao.insertFavorite(favoriteEntity)
    }

    override suspend fun removeFromFavorites(postId: Int) {
        favoritePostDao.deleteFavorite(postId)
    }

    override suspend fun getFavorites(): List<FavoritePost> {
        return favoritePostDao.getAllFavorites().map { entity ->
            FavoritePost(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                body = entity.body,
                createdAt = entity.createdAt
            )
        }
    }

    override suspend fun isFavorite(postId: Int): Boolean {
        return favoritePostDao.isFavorite(postId)
    }

    override suspend fun getFavoriteIds(): Set<Int> {
        return favoritePostDao.getFavoriteIds().toSet()
    }
}