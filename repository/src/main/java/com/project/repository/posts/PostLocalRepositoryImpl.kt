package com.project.repository.posts

import android.content.Context
import com.project.models.posts.FavouritesRepository
import com.project.models.posts.Post
import com.project.models.posts.PostRepository
import com.project.repository.Api
import com.project.repository.RetrofitClient
import com.project.repository.favourites.FavouritesLocalRepositoryImpl

/**
 * This class is for local repository implementation
 */

class PostLocalRepositoryImpl(
    private val context: Context? = null
) : PostRepository {

    private val api: Api by lazy {
        RetrofitClient.instance.create(Api::class.java)
    }

    private val favouritesRepository: FavouritesRepository? by lazy {
        context?.let { FavouritesLocalRepositoryImpl(it) }
    }

    override suspend fun getPosts(page: Int, limit: Int): List<Post> {
        val posts = api.getPosts(page, limit)
        return if (favouritesRepository != null) {
            addFavoriteStatus(posts)
        } else {
            posts
        }
    }

    override suspend fun getPost(id: Int): Post {
        val post = api.getPost(id)
        return if (favouritesRepository != null) {
            post.copy(isFavorite = favouritesRepository!!.isFavorite(id))
        } else {
            post
        }
    }

    override suspend fun getAllPosts(): List<Post> {
        val posts = api.getAllPosts()
        return if (favouritesRepository != null) {
            addFavoriteStatus(posts)
        } else {
            posts
        }
    }

    private suspend fun addFavoriteStatus(posts: List<Post>): List<Post> {
        val favoriteIds = favouritesRepository?.getFavoriteIds() ?: emptySet()
        return posts.map { post ->
            post.copy(isFavorite = favoriteIds.contains(post.id))
        }
    }
}