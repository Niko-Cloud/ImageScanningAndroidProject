package com.ch2ps126.tutorin.data.di

import android.content.Context
import com.ch2ps126.tutorin.data.local.db.room.BookmarkRoomDatabase
import com.ch2ps126.tutorin.data.remote.api.ApiConfig
import com.ch2ps126.tutorin.data.remote.repository.BookmarkRepository
import com.ch2ps126.tutorin.data.remote.repository.EquipmentRepository
import com.ch2ps126.tutorin.data.remote.repository.MuscleRepository

object Injection {
    fun provideEquipmentRepository(context: Context): EquipmentRepository {
        val apiService = ApiConfig.getApiService()
        return EquipmentRepository.getInstance(apiService)
    }

    fun provideBookmarkRepository(context: Context): BookmarkRepository {
        val bookmarkDao = BookmarkRoomDatabase.getInstance(context).bookmarkDao()
        return BookmarkRepository.getInstance(bookmarkDao)
    }

    fun provideMuscleRepository(context: Context): MuscleRepository {
        val apiService = ApiConfig.getApiService()
        return MuscleRepository.getInstance(apiService)
    }

}