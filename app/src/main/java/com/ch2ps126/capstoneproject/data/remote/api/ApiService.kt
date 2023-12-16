package com.ch2ps126.capstoneproject.data.remote.api

import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponseItem
import com.ch2ps126.capstoneproject.data.remote.response.MuscleResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/equipment")
    suspend fun getAllEquipment(): List<EquipmentResponseItem>

    @GET("/equipment/{id}")
    suspend fun getEquipmentById(@Path("id") id: Int): EquipmentResponseItem

    @GET("/search")
    suspend fun searchEquipment(
        @Query("q") query: String,
        @Query("muscleTypes") muscleTypes: String,
        @Query("sort") sort: String
    ): List<EquipmentResponseItem>

    @GET("/muscles")
    suspend fun getAllMuscles(): List<MuscleResponseItem?>?
}