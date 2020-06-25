package com.example.mobilechallenge.view.ui.detail

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.repositories.Repository

class DetailViewModel(@NonNull private val repo: Repository) : ViewModel() {

    private val game = MutableLiveData<Game>()

    init {
        game.value = Game()
    }

    fun setId(id: Int) {
        fetchGame(id)
    }

    fun getGame() = game

    private fun fetchGame(id: Int) {
        repo.getGameById(id, { res ->
            game.value = res
        }, { error ->
            error.printStackTrace()
        })
    }
}