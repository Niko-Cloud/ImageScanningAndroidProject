package com.ch2ps126.capstoneproject.data.remote.api

import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun getAllEquipment(): List<EquipmentResponseItem>

    @GET("/{id}")
    suspend fun getEquipmentById(@Path("id") id: Int): EquipmentResponseItem

    @GET("/search")
    suspend fun searchEquipment(
        @Query("q") query: String,
        @Query("muscleTypes") muscleTypes: String,
        @Query("sort") sort: String
    ): List<EquipmentResponseItem>
}