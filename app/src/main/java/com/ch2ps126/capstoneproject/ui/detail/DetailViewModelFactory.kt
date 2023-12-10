package com.ch2ps126.capstoneproject.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps126.capstoneproject.data.di.Injection
import com.ch2ps126.capstoneproject.data.remote.repository.BookmarkRepository
import com.ch2ps126.capstoneproject.data.remote.repository.EquipmentRepository

class DetailViewModelFactory(
    private val equipmentRepository: EquipmentRepository,
    private val bookmarkRepository: BookmarkRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(equipmentRepository, bookmarkRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DetailViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): DetailViewModelFactory {
            if (INSTANCE == null) {
                synchronized(DetailViewModelFactory::class.java) {
                    INSTANCE = DetailViewModelFactory(
                        Injection.provideEquipmentRepository(context),
                        Injection.provideBookmarkRepository(context)
                    )
                }
            }
            return INSTANCE as DetailViewModelFactory
        }
    }
}

