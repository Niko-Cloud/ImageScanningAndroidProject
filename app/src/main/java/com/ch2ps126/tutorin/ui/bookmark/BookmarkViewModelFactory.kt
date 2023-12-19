package com.ch2ps126.tutorin.ui.bookmark

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps126.tutorin.data.di.Injection
import com.ch2ps126.tutorin.data.remote.repository.BookmarkRepository

class BookmarkViewModelFactory(private val repository: BookmarkRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                BookmarkViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: BookmarkViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): BookmarkViewModelFactory {
            if (INSTANCE == null) {
                synchronized(BookmarkViewModelFactory::class.java) {
                    INSTANCE =
                        BookmarkViewModelFactory(Injection.provideBookmarkRepository(context))
                }
            }
            return INSTANCE as BookmarkViewModelFactory
        }
    }
}