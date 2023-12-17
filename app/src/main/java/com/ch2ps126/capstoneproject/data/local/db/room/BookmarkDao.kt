package com.ch2ps126.capstoneproject.data.local.db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch2ps126.capstoneproject.data.local.db.entity.Bookmark

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookmark: Bookmark)

    @Update
    suspend fun update(bookmark: Bookmark)

    @Query("DELETE FROM Bookmark WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT EXISTS (SELECT * FROM Bookmark WHERE id = :bookmarkId)")
    fun checkBookmarkExists(bookmarkId: Int): LiveData<Boolean>

    @Query("SELECT * FROM Bookmark")
    suspend fun getBookmarkData(): List<Bookmark>

    @Query("SELECT * FROM Bookmark WHERE name LIKE '%' || :searchQuery || '%'")
    suspend fun searchBookmarks(searchQuery: String): List<Bookmark>
}