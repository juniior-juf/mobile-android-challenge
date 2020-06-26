package com.example.mobilechallenge.view.ui.main

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.repositories.Repository

class MainViewModel(@NonNull private val repo: Repository) : ViewModel() {

    private var banners = MutableLiveData<List<Banner>>()
    private var games = MutableLiveData<List<Game>>()
    private var countItemsInCart = repo.getAllItemsCart()

    init {
        banners.value = emptyList()
        games.value = emptyList()
        fetchAllBanners()
        fetchAllGames()
    }

    fun getBanners() = banners

    fun getGames() = games

    fun getCountItemsInCart() = countItemsInCart

    fun fetchAllBanners() {
        repo.getAllBanners({ res ->
            banners.value = res
        }, { error ->
            error.printStackTrace()
        })
    }

    fun fetchAllGames() {
        repo.getAllGames({ res ->
            games.value = res
        }, { error ->
            error.printStackTrace()
        })
    }
}
