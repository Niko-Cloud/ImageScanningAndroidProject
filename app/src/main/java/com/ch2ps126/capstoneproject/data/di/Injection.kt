package com.ch2ps126.capstoneproject.data.di

import android.content.Context
import com.ch2ps126.capstoneproject.data.local.db.room.BookmarkDao
import com.ch2ps126.capstoneproject.data.local.db.room.BookmarkRoomDatabase
import com.ch2ps126.capstoneproject.data.remote.api.ApiConfig
import com.ch2ps126.capstoneproject.data.remote.repository.BookmarkRepository
import com.ch2ps126.capstoneproject.data.remote.repository.EquipmentRepository

object Injection {
    fun provideEquipmentRepository(context: Context): EquipmentRepository {
        val apiService = ApiConfig.getApiService()
        return EquipmentRepository.getInstance(apiService)
    }

    fun provideBookmarkRepository(context: Context): BookmarkRepository {
        val bookmarkDao = BookmarkRoomDatabase.getInstance(context).bookmarkDao()
        return BookmarkRepository.getInstance(bookmarkDao)
    }
}