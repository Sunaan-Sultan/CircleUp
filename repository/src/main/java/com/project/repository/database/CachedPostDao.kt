package com.project.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedPostDao {

    @Query("SELECT * FROM cached_posts ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getCachedPosts(limit: Int, offset: Int): List<CachedPostEntity>

    @Query("SELECT * FROM cached_posts ORDER BY id ASC")
    suspend fun getAllCachedPosts(): List<CachedPostEntity>

    @Query("SELECT * FROM cached_posts WHERE id = :postId LIMIT 1")
    suspend fun getCachedPost(postId: Int): CachedPostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<CachedPostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CachedPostEntity)

    @Query("UPDATE cached_posts SET isFavorite = :isFavorite WHERE id = :postId")
    suspend fun updateFavoriteStatus(postId: Int, isFavorite: Boolean)

    @Query("DELETE FROM cached_posts")
    suspend fun clearCache()

    @Query("SELECT COUNT(*) FROM cached_posts")
    suspend fun getCacheSize(): Int

    @Query("SELECT EXISTS(SELECT 1 FROM cached_posts LIMIT 1)")
    suspend fun hasCachedData(): Boolean

    @Query("SELECT * FROM cached_posts WHERE title LIKE '%' || :query || '%' OR body LIKE '%' || :query || '%' ORDER BY id ASC")
    suspend fun searchCachedPosts(query: String): List<CachedPostEntity>

    @Query("DELETE FROM cached_posts WHERE cachedAt < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)
}