package com.project.repository.cacheposts

import com.project.models.posts.Post
import com.project.models.posts.PostCacheRepository

/**
 * This class is for live repository implementation
 */

class PostCacheRepositoryImpl : PostCacheRepository  {
    override suspend fun cachePosts(posts: List<Post>, page: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getCachedPosts(page: Int, limit: Int): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCachedPosts(): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getCachedPost(id: Int): Post? {
        TODO("Not yet implemented")
    }

    override suspend fun clearCache() {
        TODO("Not yet implemented")
    }

    override suspend fun getCacheSize(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun hasCachedData(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updatePostFavoriteStatus(postId: Int, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }
}