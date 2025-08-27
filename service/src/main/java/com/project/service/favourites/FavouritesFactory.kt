package com.project.service.favourites

import android.content.Context
import com.project.models.posts.FavouritesRepository
import com.project.repository.favourites.FavouritesLocalRepositoryImpl
import com.project.repository.favourites.FavouritesRepositoryImpl
import com.project.service.RuntimeProfile
import com.project.service.RuntimeProfile.LIVE_RUNTIME

object FavouritesFactory {

    fun getFavoritesRepository(context: Context): FavouritesRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            FavouritesRepositoryImpl()
        } else {
            FavouritesLocalRepositoryImpl(context)
        }
    }
}