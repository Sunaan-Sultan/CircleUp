
package com.project.service.posts

import com.project.models.posts.Post
import com.project.models.posts.PostRepository
import com.project.models.posts.PostService
import com.project.repository.posts.PostLocalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostServiceImpl(
    private val repository: PostRepository = PostLocalRepositoryImpl()
) : PostService {

    override suspend fun getPosts(page: Int, limit: Int): List<Post> =
        withContext(Dispatchers.IO) {
            try {
                repository.getPosts(page, limit)
            } catch (e: Exception) {
                throw Exception("Failed to load posts: ${e.message}")
            }
        }

    override suspend fun getPost(id: Int): Post =
        withContext(Dispatchers.IO) {
            try {
                repository.getPost(id)
            } catch (e: Exception) {
                throw Exception("Failed to load post: ${e.message}")
            }
        }

    override suspend fun getAllPosts(): List<Post> =
        withContext(Dispatchers.IO) {
            try {
                repository.getAllPosts()
            } catch (e: Exception) {
                throw Exception("Failed to load all posts: ${e.message}")
            }
        }
}