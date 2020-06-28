package com.example.mobilechallenge.view.ui.detail

import android.database.sqlite.SQLiteConstraintException
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.models.ItemCart
import com.example.mobilechallenge.data.repositories.Repository
import kotlinx.coroutines.launch

class DetailViewModel(@NonNull private val repo: Repository) : ViewModel() {

    private val game = MutableLiveData<Game>()

    val inCart = MutableLiveData<Boolean>().apply { postValue(false) }

    init {
        game.value = Game()
    }

    fun setId(id: Int) {
        fetchGameDetail(id)
        verifyItemContainsInCart(id)
    }

    fun getGame() = game

    fun fetchGameDetail(id: Int) {
        repo.getGameById(id, { res ->
            game.value = res
        }, { error ->
            error.printStackTrace()
        })
    }

    fun verifyItemContainsInCart(id: Int) {
        viewModelScope.launch {
            val item = repo.getItemCart(id)
            inCart.value = item != null
        }
    }

    fun onClickInsertOrDeleteItem() {
        if (game.value!!.id != 0) {

            val item = game.value?.let {
                ItemCart(it.id, it.title, it.image, it.price, it.discount, 1)
            } as ItemCart

            insertOrDeleteItem(item)
        }
    }

    fun insertOrDeleteItem(item: ItemCart) {
        viewModelScope.launch {
            try {
                repo.insertItemCart(item)
                inCart.value = true
            } catch (ex: SQLiteConstraintException) {
                repo.deleteItemCart(item)
                inCart.value = false
            }
        }
    }

    override fun onCleared() {
        repo.clearCompositeDisposable()
        super.onCleared()
    }
}