package com.example.mobilechallenge.view.ui.shopping_cart

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.data.repositories.Repository

class ShoppingCarViewModel(@NonNull private val repo: Repository) : ViewModel() {

    private val itemsCart = repo.getAllItemsCart()

    fun getItemsCart() = itemsCart
}