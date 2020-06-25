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

    init {
        banners.value = emptyList()
        fetchAllBanners()
        fetchAllGames()
    }

    fun getBanners() = banners

    fun getGames() = games

    private fun fetchAllBanners() {
        repo.getBanners({ res ->
            banners.value = res
        }, { error ->
            error.printStackTrace()
        })
    }

    private fun fetchAllGames() {
        repo.getGames({ res ->
            games.value = res
        }, { error ->
            error.printStackTrace()
        })
    }
}
