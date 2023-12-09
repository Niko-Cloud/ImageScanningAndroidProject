package com.ch2ps126.capstoneproject.data.remote.repository

import com.ch2ps126.capstoneproject.data.remote.api.ApiService
import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponse
import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponseItem

class EquipmentRepository private constructor(private val apiService: ApiService){
    suspend fun getAllEquipment(): List<EquipmentResponseItem> {
        return apiService.getAllEquipment()
    }

    suspend fun getEquipmentById(id: Int): EquipmentResponseItem {
        return apiService.getEquipmentById(id)
    }

    suspend fun searchEquipment(query: String, muscleTypes: String, sort: String): List<EquipmentResponseItem> {
        return apiService.searchEquipment(query, muscleTypes, sort)
    }

    companion object {
        @Volatile
        private var instance: EquipmentRepository? = null
        fun getInstance(
            apiService: ApiService
        ): EquipmentRepository =
            instance ?: synchronized(this) {
                instance ?: EquipmentRepository(apiService)
            }.also { instance = it }
    }
}