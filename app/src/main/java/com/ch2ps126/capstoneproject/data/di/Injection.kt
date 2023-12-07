package com.ch2ps126.capstoneproject.data.di

import android.content.Context
import com.ch2ps126.capstoneproject.data.remote.api.ApiConfig
import com.ch2ps126.capstoneproject.data.remote.repository.EquipmentRepository

object Injection {
    fun provideRepository(context: Context): EquipmentRepository {
        val apiService = ApiConfig.getApiService()
        return EquipmentRepository.getInstance(apiService)
    }
}