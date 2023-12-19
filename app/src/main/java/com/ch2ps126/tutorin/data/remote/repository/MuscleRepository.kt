package com.ch2ps126.tutorin.data.remote.repository

import com.ch2ps126.tutorin.data.remote.api.ApiService
import com.ch2ps126.tutorin.data.remote.response.MuscleResponseItem

class MuscleRepository private constructor(private val apiService: ApiService){

    suspend fun getAllMuscles(): List<MuscleResponseItem?>? {
        return apiService.getAllMuscles()
    }

    companion object {
        @Volatile
        private var instance: MuscleRepository? = null
        fun getInstance(
            apiService: ApiService
        ): MuscleRepository =
            instance ?: synchronized(this) {
                instance ?: MuscleRepository(apiService)
            }.also { instance = it }
    }
}