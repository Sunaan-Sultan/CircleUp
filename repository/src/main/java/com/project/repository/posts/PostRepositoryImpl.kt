package com.project.repository.posts

import com.project.models.posts.Post
import com.project.models.posts.PostRepository

/**
 * This class is for live repository implementation
 */

class PostRepositoryImpl : PostRepository {
    override suspend fun getPosts(page: Int, limit: Int): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(id: Int): Post {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPosts(): List<Post> {
        TODO("Not yet implemented")
    }

}