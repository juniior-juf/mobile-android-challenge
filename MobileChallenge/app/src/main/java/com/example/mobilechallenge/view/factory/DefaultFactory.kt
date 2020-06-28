package com.example.mobilechallenge.view.factory

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobilechallenge.data.repositories.Repository
import com.example.mobilechallenge.data.repositories.RepositoryImpl
import com.example.mobilechallenge.view.ui.detail.DetailViewModel
import com.example.mobilechallenge.view.ui.main.MainViewModel
import com.example.mobilechallenge.view.ui.shopping_cart.ShoppingCarViewModel
import java.lang.IllegalArgumentException

class DefaultFactory(@NonNull private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(repository)
                }

                isAssignableFrom(DetailViewModel::class.java) -> {
                    DetailViewModel(repository)
                }

                isAssignableFrom(ShoppingCarViewModel::class.java) -> {
                    ShoppingCarViewModel(repository)
                }
                else -> throw IllegalArgumentException("Unknown class")
            }
        } as T
    }
}