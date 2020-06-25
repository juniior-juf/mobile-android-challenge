package com.example.mobilechallenge.view.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.data.repositories.BannerRepository
import com.example.mobilechallenge.view.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class MainFactory(@NonNull private val repository: BannerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(repository)
                }
                else -> throw IllegalArgumentException("Unknown class")
            }
        } as T
    }
}