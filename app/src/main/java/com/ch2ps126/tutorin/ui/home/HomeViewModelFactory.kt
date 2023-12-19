package com.ch2ps126.tutorin.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps126.tutorin.data.di.Injection
import com.ch2ps126.tutorin.data.remote.repository.EquipmentRepository
import com.ch2ps126.tutorin.data.remote.repository.MuscleRepository

class HomeViewModelFactory(private val repository: EquipmentRepository, private val muscleRepository: MuscleRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository,muscleRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): HomeViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HomeViewModelFactory::class.java) {
                    INSTANCE = HomeViewModelFactory(
                        Injection.provideEquipmentRepository(context),
                        Injection.provideMuscleRepository(context)
                    )
                }
            }
            return INSTANCE as HomeViewModelFactory
        }
    }
}