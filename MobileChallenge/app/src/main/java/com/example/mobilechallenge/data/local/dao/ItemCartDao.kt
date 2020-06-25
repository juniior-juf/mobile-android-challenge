package com.example.mobilechallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mobilechallenge.data.models.ItemCart

@Dao
interface ItemCartDao {

    @Insert
    suspend fun insertItemCar(itemCart: ItemCart)

    @Query("SELECT * FROM items_cart_table WHERE id=:id")
    suspend fun getItemCart(id: Int): ItemCart

    @Query("SELECT * FROM items_cart_table")
    fun getAllItemsCart(): List<ItemCart>

    @Delete
    suspend fun deleteItemCart(itemCart: ItemCart)

    @Query("DELETE FROM items_cart_table")
    suspend fun deleteAllItemsCart()
}