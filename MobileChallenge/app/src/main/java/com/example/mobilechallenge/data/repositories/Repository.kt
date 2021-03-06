package com.example.mobilechallenge.data.repositories

import androidx.lifecycle.LiveData
import com.example.mobilechallenge.data.models.Banner
import com.example.mobilechallenge.data.models.Game
import com.example.mobilechallenge.data.models.ItemCart
import com.google.gson.JsonObject

interface Repository {

    fun getAllBanners(success: (List<Banner>) -> Unit, failed: (Throwable) -> Unit)

    fun getAllGames(success: (List<Game>) -> Unit, failed: (Throwable) -> Unit)

    fun getGameById(id: Int, success: (Game) -> Unit, failed: (Throwable) -> Unit)

    fun searchGames(title:String, success: (List<Game>) -> Unit, failed: (Throwable) -> Unit)

    fun checkout(data: JsonObject, success: () -> Unit, failed: (Throwable) -> Unit)

    suspend fun insertItemCart(itemCart: ItemCart)

    suspend fun getItemCart(id: Int): ItemCart?

    fun getItemsCount():LiveData<Int>

    fun getAllItemsCart(): LiveData<List<ItemCart>>

    suspend fun updateItemCart(amount: Int, id: Int)

    suspend fun deleteItemCart(itemCart: ItemCart)

    suspend fun deleteAllItemsCart()

    fun clearCompositeDisposable()
}