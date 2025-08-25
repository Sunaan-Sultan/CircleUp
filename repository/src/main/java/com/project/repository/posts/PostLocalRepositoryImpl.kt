package com.project.repository.posts

import android.content.Context
import com.project.models.posts.FavoritesRepository
import com.project.models.posts.Post
import com.project.models.posts.PostRepository
import com.project.repository.Api
import com.project.repository.RetrofitClient
import com.project.repository.favourites.FavoritesLocalRepositoryImpl

class PostLocalRepositoryImpl(
    private val context: Context? = null
) : PostRepository {

    private val api: Api by lazy {
        RetrofitClient.instance.create(Api::class.java)
    }

    private val favoritesRepository: FavoritesRepository? by lazy {
        context?.let { FavoritesLocalRepositoryImpl(it) }
    }

    override suspend fun getPosts(page: Int, limit: Int): List<Post> {
        val posts = api.getPosts(page, limit)
        return if (favoritesRepository != null) {
            addFavoriteStatus(posts)
        } else {
            posts
        }
    }

    override suspend fun getPost(id: Int): Post {
        val post = api.getPost(id)
        return if (favoritesRepository != null) {
            post.copy(isFavorite = favoritesRepository!!.isFavorite(id))
        } else {
            post
        }
    }

    override suspend fun getAllPosts(): List<Post> {
        val posts = api.getAllPosts()
        return if (favoritesRepository != null) {
            addFavoriteStatus(posts)
        } else {
            posts
        }
    }

    private suspend fun addFavoriteStatus(posts: List<Post>): List<Post> {
        val favoriteIds = favoritesRepository?.getFavoriteIds() ?: emptySet()
        return posts.map { post ->
            post.copy(isFavorite = favoriteIds.contains(post.id))
        }
    }
}