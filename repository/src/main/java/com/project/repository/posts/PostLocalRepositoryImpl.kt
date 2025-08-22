package com.project.repository.posts

import com.project.models.posts.Post
import com.project.models.posts.PostRepository
import com.project.repository.Api
import com.project.repository.RetrofitClient

class PostLocalRepositoryImpl : PostRepository {

    private val api: Api by lazy {
        RetrofitClient.instance.create(Api::class.java)
    }

    override suspend fun getPosts(page: Int, limit: Int): List<Post> {
        return api.getPosts(page, limit)
    }

    override suspend fun getPost(id: Int): Post {
        return api.getPost(id)
    }

    override suspend fun getAllPosts(): List<Post> {
        return api.getAllPosts()
    }
}