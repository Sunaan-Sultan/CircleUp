package com.project.models.posts

interface PostRepository {
    suspend fun getPosts(page: Int, limit: Int): List<Post>
    suspend fun getPost(id: Int): Post
    suspend fun getAllPosts(): List<Post>
}