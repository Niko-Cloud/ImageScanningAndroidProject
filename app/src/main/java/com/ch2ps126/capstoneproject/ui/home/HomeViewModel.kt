package com.ch2ps126.capstoneproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps126.capstoneproject.data.remote.repository.EquipmentRepository
import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponseItem
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EquipmentRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _equipmentData = MutableLiveData<List<EquipmentResponseItem>>()
    val equipmentData: LiveData<List<EquipmentResponseItem>> = _equipmentData

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private val _muscleTypes = MutableLiveData<String>()
    val muscleTypes: LiveData<String> = _muscleTypes

    private val _sort = MutableLiveData<String>()
    val sort: LiveData<String> = _sort

    suspend fun getAllEquipment() {
        _isLoading.value = true
        try {
            val data = repository.getAllEquipment()
            _equipmentData.value = data
            _isLoading.value = false
        } catch (e: Exception) {
            Log.d("HomeViewModel", "Error: ${e.message}")
            _isLoading.value = false
            throw e
        }
    }

//    fun filterEquipment(query: String, muscleTypes: String, sort: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val data = repository.searchEquipment(query, muscleTypes, sort)
//                _equipmentData.value = data
//                _isLoading.value = false
//            } catch (e: Exception) {
//                Log.d("HomeViewModel", "Error: ${e.message}")
//                _isLoading.value = false
//                throw e
//            }
//        }
//    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setMuscleTypes(muscleTypes: String) {
        _muscleTypes.value = muscleTypes
    }

    fun setSort(sort: String) {
        _sort.value = sort
    }
    fun filterEquipment() {
        val query = _searchQuery.value ?: ""
        val muscleTypes = _muscleTypes.value ?: ""
        val sort = _sort.value ?: ""

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = repository.searchEquipment(query, muscleTypes, sort)
                Log.d("HomeViewModel", "Error: $data")
                _equipmentData.value = data
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("HomeViewModel", "Error: ${e.message}")
                _isLoading.value = false
                throw e
            }
        }
    }
}