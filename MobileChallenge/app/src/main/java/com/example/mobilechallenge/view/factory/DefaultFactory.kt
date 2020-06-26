package com.example.mobilechallenge.view.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.data.repositories.RepositoryImpl
import com.example.mobilechallenge.view.ui.detail.DetailViewModel
import com.example.mobilechallenge.view.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class DefaultFactory(@NonNull private val repositoryImpl: RepositoryImpl) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(repositoryImpl)
                }

                isAssignableFrom(DetailViewModel::class.java) -> {
                    DetailViewModel(repositoryImpl)
                }
                else -> throw IllegalArgumentException("Unknown class")
            }
        } as T
    }
}