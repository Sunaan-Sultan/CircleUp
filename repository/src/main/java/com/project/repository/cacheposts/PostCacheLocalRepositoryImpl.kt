package com.project.repository.cacheposts

import android.content.Context
import com.project.models.posts.Post
import com.project.models.posts.PostCacheRepository
import com.project.repository.database.CachedPostDao
import com.project.repository.database.CachedPostEntity
import com.project.repository.database.DatabaseModule

class PostCacheLocalRepositoryImpl(
    private val context: Context
) : PostCacheRepository {

    private val cachedPostDao: CachedPostDao by lazy {
        DatabaseModule.provideCachedPostDao(context)
    }

    override suspend fun cachePosts(posts: List<Post>, page: Int) {
        val cachedEntities = posts.map { post ->
            CachedPostEntity(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body,
                isFavorite = post.isFavorite,
                cachedAt = System.currentTimeMillis(),
                page = page
            )
        }
        cachedPostDao.insertPosts(cachedEntities)
    }

    override suspend fun getCachedPosts(page: Int, limit: Int): List<Post> {
        val offset = (page - 1) * limit
        return cachedPostDao.getCachedPosts(limit, offset).map { entity ->
            Post(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                body = entity.body,
                isFavorite = entity.isFavorite
            )
        }
    }

    override suspend fun getAllCachedPosts(): List<Post> {
        return cachedPostDao.getAllCachedPosts().map { entity ->
            Post(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                body = entity.body,
                isFavorite = entity.isFavorite
            )
        }
    }

    override suspend fun getCachedPost(id: Int): Post? {
        val entity = cachedPostDao.getCachedPost(id)
        return entity?.let {
            Post(
                id = it.id,
                userId = it.userId,
                title = it.title,
                body = it.body,
                isFavorite = it.isFavorite
            )
        }
    }

    override suspend fun clearCache() {
        cachedPostDao.clearCache()
    }

    override suspend fun getCacheSize(): Int {
        return cachedPostDao.getCacheSize()
    }

    override suspend fun hasCachedData(): Boolean {
        return cachedPostDao.hasCachedData()
    }

    override suspend fun updatePostFavoriteStatus(postId: Int, isFavorite: Boolean) {
        cachedPostDao.updateFavoriteStatus(postId, isFavorite)
    }

    // Helper method for searching cached posts
    suspend fun searchCachedPosts(query: String): List<Post> {
        return cachedPostDao.searchCachedPosts(query).map { entity ->
            Post(
                id = entity.id,
                userId = entity.userId,
                title = entity.title,
                body = entity.body,
                isFavorite = entity.isFavorite
            )
        }
    }

    // Helper method to clean old cache (older than 24 hours)
    suspend fun cleanOldCache() {
        val oneDayAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
        cachedPostDao.deleteOldCache(oneDayAgo)
    }
}