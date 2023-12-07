package com.ch2ps126.capstoneproject.data.remote.repository

import androidx.lifecycle.LiveData
import com.ch2ps126.capstoneproject.data.local.db.entity.Bookmark
import com.ch2ps126.capstoneproject.data.local.db.room.BookmarkDao

class BookmarkRepository private constructor(private val bookmarkDao: BookmarkDao) {
    suspend fun getBookmarkData(): List<Bookmark> {
        return bookmarkDao.getBookmarkData()
    }

    fun getBookmarkExists(bookmarkId: Int): LiveData<Boolean> {
        return bookmarkDao.checkBookmarkExists(bookmarkId)
    }

    suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarkDao.insert(bookmark)
    }

    suspend fun deleteBookmark(id: Int) {
        bookmarkDao.deleteById(id)
    }

    companion object {
        @Volatile
        private var instance: BookmarkRepository? = null
        fun getInstance(
            bookmarkDao: BookmarkDao
        ): BookmarkRepository =
            instance ?: synchronized(this) {
                instance ?: BookmarkRepository(bookmarkDao)
            }.also { instance = it }
    }
}