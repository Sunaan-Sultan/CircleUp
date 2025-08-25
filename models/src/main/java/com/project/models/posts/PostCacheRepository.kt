package com.project.models.posts

interface PostCacheRepository {
    suspend fun cachePosts(posts: List<Post>, page: Int)
    suspend fun getCachedPosts(page: Int, limit: Int): List<Post>
    suspend fun getAllCachedPosts(): List<Post>
    suspend fun getCachedPost(id: Int): Post?
    suspend fun clearCache()
    suspend fun getCacheSize(): Int
    suspend fun hasCachedData(): Boolean
    suspend fun updatePostFavoriteStatus(postId: Int, isFavorite: Boolean)
}