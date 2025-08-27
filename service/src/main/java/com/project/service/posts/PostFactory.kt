package com.project.service.posts

import android.content.Context
import com.project.models.posts.FavouritesRepository
import com.project.models.posts.PostCacheRepository
import com.project.models.posts.PostRepository
import com.project.repository.cacheposts.PostCacheLocalRepositoryImpl
import com.project.repository.cacheposts.PostCacheRepositoryImpl
import com.project.repository.favourites.FavouritesLocalRepositoryImpl
import com.project.repository.favourites.FavouritesRepositoryImpl
import com.project.repository.posts.PostLocalRepositoryImpl
import com.project.repository.posts.PostRepositoryImpl
import com.project.service.RuntimeProfile
import com.project.service.RuntimeProfile.LIVE_RUNTIME

object PostFactory {

    fun getPostRepository(context: Context?): PostRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            PostRepositoryImpl()
        } else {
            context?.let { PostLocalRepositoryImpl(it) }
                ?: throw IllegalArgumentException("Context required for local repository")
        }
    }

    fun getPostCacheRepository(context: Context): PostCacheRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            PostCacheRepositoryImpl()
        } else {
            PostCacheLocalRepositoryImpl(context)
        }
    }

    fun getFavoritesRepository(context: Context): FavouritesRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            FavouritesRepositoryImpl()
        } else {
            FavouritesLocalRepositoryImpl(context)
        }
    }
}