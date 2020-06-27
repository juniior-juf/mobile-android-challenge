package com.example.mobilechallenge.view.ui.main

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.repositories.Repository

class MainViewModel(@NonNull private val repo: Repository) : ViewModel() {

    private lateinit var listener: MainBase

    private var banners = MutableLiveData<List<Banner>>()
    private var games = MutableLiveData<List<Game>>()
    private var listSearchGames = MutableLiveData<List<Game>>()
    private var itemsCart = repo.getAllItemsCart()
    val loading = MutableLiveData<Boolean>().apply { postValue(true) }

    init {
        banners.value = emptyList()
        games.value = emptyList()
        listSearchGames.value = emptyList()
        fetchAllBanners()
        fetchAllGames()
    }

    fun setListener(listener: MainBase) {
        this.listener = listener
    }

    fun getBanners() = banners

    fun getGames() = games

    fun getListSearchGames() = listSearchGames

    fun getItemsCart() = itemsCart

    fun fetchAllBanners() {
        repo.getAllBanners({ res ->
            banners.value = res
            loading.value = false
        }, { error ->
            error.printStackTrace()
            loading.value = false
        })
    }

    fun fetchAllGames() {
        repo.getAllGames({ res ->
            games.value = res
            loading.value = false
        }, { error ->
            error.printStackTrace()
            loading.value = false
        })
    }

    fun searchGames(title: String) {
        repo.searchGames(title, { res ->
            listSearchGames.value = res
            if (res.isEmpty())
                listener.showMessageSearch("Nenhum game encontrado")
        }, { error ->
            error.printStackTrace()
            listener.showMessageSearch("Ocorreu algum problema durante a busca dos games.\nTente novamente!")
        })
    }
}
