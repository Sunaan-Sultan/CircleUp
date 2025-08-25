package com.project.models.posts

interface PostService {
    suspend fun getPosts(page: Int, limit: Int): List<Post>
    suspend fun getPost(id: Int): Post
    suspend fun getAllPosts(): List<Post>
    suspend fun toggleFavorite(post: Post): Boolean
    suspend fun getPostsWithFavorites(page: Int, limit: Int): List<Post>
    suspend fun getAllPostsWithFavorites(): List<Post>

    suspend fun getPostsOffline(page: Int, limit: Int): List<Post>
    suspend fun getAllPostsOffline(): List<Post>
    suspend fun syncCacheWithFavorites()
    suspend fun hasCachedData(): Boolean
}