
package com.project.service.posts

import android.content.Context
import com.project.models.posts.FavoritesRepository
import com.project.models.posts.Post
import com.project.models.posts.PostCacheRepository
import com.project.models.posts.PostRepository
import com.project.models.posts.PostService
import com.project.repository.NetworkUtil
import com.project.repository.cacheposts.PostCacheLocalRepositoryImpl
import com.project.repository.favourites.FavoritesLocalRepositoryImpl
import com.project.repository.posts.PostLocalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostServiceImpl(
    private val context: Context? = null,
    private val repository: PostRepository = PostLocalRepositoryImpl(context),
    private val cacheRepository: PostCacheRepository? = context?.let { PostCacheLocalRepositoryImpl(it) },
    private val favoritesRepository: FavoritesRepository? = context?.let { FavoritesLocalRepositoryImpl(it) }
) : PostService {

    override suspend fun getPosts(page: Int, limit: Int): List<Post> =
        withContext(Dispatchers.IO) {
            try {
                if (context != null && NetworkUtil.isNetworkAvailable(context)) {
                    val posts = repository.getPosts(page, limit)
                    cacheRepository?.cachePosts(posts, page)
                    posts
                } else {
                    getPostsOffline(page, limit)
                }
            } catch (e: Exception) {
                if (context != null && cacheRepository?.hasCachedData() == true) {
                    getPostsOffline(page, limit)
                } else {
                    throw Exception("No internet connection and no cached data available")
                }
            }
        }

    override suspend fun getPost(id: Int): Post =
        withContext(Dispatchers.IO) {
            try {
                if (context != null && NetworkUtil.isNetworkAvailable(context)) {
                    repository.getPost(id)
                } else {
                    cacheRepository?.getCachedPost(id)
                        ?: throw Exception("Post not found in cache")
                }
            } catch (e: Exception) {
                cacheRepository?.getCachedPost(id)
                    ?: throw Exception("Post not available offline")
            }
        }

    override suspend fun getAllPosts(): List<Post> =
        withContext(Dispatchers.IO) {
            try {
                if (context != null && NetworkUtil.isNetworkAvailable(context)) {
                    val posts = repository.getAllPosts()
                    cacheRepository?.cachePosts(posts, 0)
                    posts
                } else {
                    getAllPostsOffline()
                }
            } catch (e: Exception) {
                if (context != null && cacheRepository?.hasCachedData() == true) {
                    getAllPostsOffline()
                } else {
                    throw Exception("No internet connection and no cached data available")
                }
            }
        }

    override suspend fun toggleFavorite(post: Post): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val newFavoriteStatus = favoritesRepository?.let { repo ->
                    if (post.isFavorite) {
                        repo.removeFromFavorites(post.id)
                        false
                    } else {
                        repo.addToFavorites(post)
                        true
                    }
                } ?: false

                cacheRepository?.updatePostFavoriteStatus(post.id, newFavoriteStatus)

                newFavoriteStatus
            } catch (e: Exception) {
                throw Exception("Failed to toggle favorite: ${e.message}")
            }
        }

    override suspend fun getPostsWithFavorites(page: Int, limit: Int): List<Post> =
        withContext(Dispatchers.IO) {
            try {
                getPosts(page, limit)
            } catch (e: Exception) {
                throw Exception("Failed to load posts with favorites: ${e.message}")
            }
        }

    override suspend fun getAllPostsWithFavorites(): List<Post> =
        withContext(Dispatchers.IO) {
            try {
                getAllPosts()
            } catch (e: Exception) {
                throw Exception("Failed to load all posts with favorites: ${e.message}")
            }
        }

    override suspend fun getPostsOffline(page: Int, limit: Int): List<Post> =
        withContext(Dispatchers.IO) {
            cacheRepository?.getCachedPosts(page, limit)
                ?: throw Exception("No cached data available")
        }

    override suspend fun getAllPostsOffline(): List<Post> =
        withContext(Dispatchers.IO) {
            cacheRepository?.getAllCachedPosts()
                ?: throw Exception("No cached data available")
        }

    override suspend fun syncCacheWithFavorites() =
        withContext(Dispatchers.IO) {
            try {
                val favoriteIds = favoritesRepository?.getFavoriteIds() ?: emptySet()
                val cachedPosts = cacheRepository?.getAllCachedPosts() ?: emptyList()

                cachedPosts.forEach { post ->
                    val shouldBeFavorite = favoriteIds.contains(post.id)
                    if (post.isFavorite != shouldBeFavorite) {
                        cacheRepository?.updatePostFavoriteStatus(post.id, shouldBeFavorite)
                    }
                }
            } catch (e: Exception) {
            }
        }

    override suspend fun hasCachedData(): Boolean =
        withContext(Dispatchers.IO) {
            cacheRepository?.hasCachedData() ?: false
        }

    suspend fun searchPosts(query: String): List<Post> =
        withContext(Dispatchers.IO) {
            if (context != null && NetworkUtil.isNetworkAvailable(context)) {
                val allPosts = getAllPosts()
                allPosts.filter { post ->
                    post.title.contains(query, ignoreCase = true) ||
                            post.body.contains(query, ignoreCase = true)
                }
            } else {
                (cacheRepository as? PostCacheLocalRepositoryImpl)?.searchCachedPosts(query)
                    ?: emptyList()
            }
        }
}