package com.ch2ps126.capstoneproject.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ch2ps126.capstoneproject.data.remote.repository.EquipmentRepository
import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponseItem

class DetailViewModel(private val repository: EquipmentRepository)  : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _equipmentData = MutableLiveData<EquipmentResponseItem>()
    val equipmentData: LiveData<EquipmentResponseItem> = _equipmentData

    suspend fun getEquipmentById(id: Int) {
        _isLoading.value = true
        try {
            val data = repository.getEquipmentById(id)
            _equipmentData.value = data
            _isLoading.value = false
        } catch (e: Exception) {
            Log.d("StoryListViewModel", "Error: ${e.message}")
            _isLoading.value = false
            throw e
        }
    }
}