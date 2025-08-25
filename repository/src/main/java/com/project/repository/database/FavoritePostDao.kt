package com.project.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePostDao {

    @Query("SELECT * FROM favorite_posts ORDER BY createdAt DESC")
    suspend fun getAllFavorites(): List<FavoritePostEntity>

    @Query("SELECT * FROM favorite_posts ORDER BY createdAt DESC")
    fun getAllFavoritesFlow(): Flow<List<FavoritePostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoritePost: FavoritePostEntity)

    @Query("DELETE FROM favorite_posts WHERE id = :postId")
    suspend fun deleteFavorite(postId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_posts WHERE id = :postId)")
    suspend fun isFavorite(postId: Int): Boolean

    @Query("SELECT id FROM favorite_posts")
    suspend fun getFavoriteIds(): List<Int>

    @Query("SELECT COUNT(*) FROM favorite_posts")
    suspend fun getFavoritesCount(): Int
}