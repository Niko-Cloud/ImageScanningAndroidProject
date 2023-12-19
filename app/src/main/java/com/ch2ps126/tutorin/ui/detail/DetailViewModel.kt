package com.ch2ps126.tutorin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps126.tutorin.data.local.db.entity.Bookmark
import com.ch2ps126.tutorin.data.remote.repository.BookmarkRepository
import com.ch2ps126.tutorin.data.remote.repository.EquipmentRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val equipmentRepository: EquipmentRepository,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

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