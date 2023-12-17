package com.ch2ps126.capstoneproject.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps126.capstoneproject.data.local.db.entity.Bookmark
import com.ch2ps126.capstoneproject.data.remote.repository.BookmarkRepository
import com.ch2ps126.capstoneproject.data.remote.repository.EquipmentRepository
import com.ch2ps126.capstoneproject.data.remote.response.EquipmentResponseItem
import kotlinx.coroutines.launch

class DetailViewModel(
    private val equipmentRepository: EquipmentRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _equipmentData = MutableLiveData<EquipmentResponseItem>()
    val equipmentData: LiveData<EquipmentResponseItem> = _equipmentData

    suspend fun getEquipmentById(id: Int) {
        _isLoading.value = true
        try {
            val data = equipmentRepository.getEquipmentById(id)
            _equipmentData.value = data
            _isLoading.value = false
        } catch (e: Exception) {
            Log.d("StoryListViewModel", "Error: ${e.message}")
            _isLoading.value = false
            throw e
        }
    }


    fun getBookmarkExists(bookmarkId: Int): LiveData<Boolean> {
        return bookmarkRepository.getBookmarkExists(bookmarkId)
    }

    fun insertBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            bookmarkRepository.insertBookmark(bookmark)
        }
    }

    fun deleteBookmark(id: Int) {
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(id)
            bookmarkRepository.getBookmarkExists(id)
        }
    }
}