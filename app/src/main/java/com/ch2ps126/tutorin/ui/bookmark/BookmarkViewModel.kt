package com.ch2ps126.tutorin.ui.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps126.tutorin.data.local.db.entity.Bookmark
import com.ch2ps126.tutorin.data.remote.repository.BookmarkRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(private val bookmarkRepository: BookmarkRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _bookmarkData = MutableLiveData<List<Bookmark>>()
    val bookmarkData: LiveData<List<Bookmark>> = _bookmarkData

    fun getAllBookmark() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = bookmarkRepository.getBookmarkData()
                _bookmarkData.value = data
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("BookmarkViewModel", "Error: ${e.message}")
                _isLoading.value = false
                throw e
            }
        }
    }

    fun searchBookmark(searchQuery: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val data = bookmarkRepository.searchBookmarks(searchQuery)
                _bookmarkData.value = data
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("BookmarkViewModel", "Error: ${e.message}")
                _isLoading.value = false
                throw e
            }
        }
    }
}
