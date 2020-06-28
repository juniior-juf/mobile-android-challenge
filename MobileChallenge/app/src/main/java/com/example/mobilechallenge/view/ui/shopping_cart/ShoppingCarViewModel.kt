package com.example.mobilechallenge.view.ui.shopping_cart

import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.data.models.ShoppingCart
import com.example.mobilechallenge.data.repositories.Repository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class ShoppingCarViewModel(@NonNull private val repo: Repository) : ViewModel() {

    companion object {
        private const val PRICE_MINIMUM_FREIGHT_FREE = 250
        private const val FREIGHT_PRICE_PER_ITEM = 10
    }

    private lateinit var listener: ShoppingCartListener
    private val shoppingCart = MutableLiveData<ShoppingCart>()
    private val itemsCart = repo.getAllItemsCart()

    init {
        shoppingCart.value = ShoppingCart()
    }

    fun setListener(listener: ShoppingCartListener) {
        this.listener = listener
    }

    fun getItemsCart() = itemsCart

    fun getShoppingCart() = shoppingCart

    fun increaseItemQuantity(position: Int) {
        val item = itemsCart.value!![position]
        val newAmount = item.amount.plus(1)
        viewModelScope.launch { repo.updateItemCart(newAmount, item.id) }
    }

    fun decreaseItemQuantity(position: Int) {
        val item = itemsCart.value!![position]
        val newAmount = item.amount.minus(1)
        if (newAmount > 0)
            viewModelScope.launch { repo.updateItemCart(newAmount, item.id) }
    }

    fun removeItem(position: Int) {
        viewModelScope.launch {
            repo.deleteItemCart(itemsCart.value!![position])
        }
    }

    fun calculateShoppingCart() {

        var initialPrice = 0
        var finalPrice = 0
        var totalItems = 0
        var freightPrice = 0

        itemsCart.value?.forEach { item ->
            initialPrice += calculateInitialPrice(item.amount, item.price)
            finalPrice += calculateFinalPrice(item.amount, item.price, item.discount)
            totalItems += item.amount
            freightPrice += (item.amount * FREIGHT_PRICE_PER_ITEM)
        }

        shoppingCart.value?.initialPrice = initialPrice
        shoppingCart.value?.totalItems = totalItems

        if (finalPrice > PRICE_MINIMUM_FREIGHT_FREE) {
            shoppingCart.value?.finalPrice = finalPrice
            shoppingCart.value?.freightPrice = 0
        } else {
            shoppingCart.value?.finalPrice = finalPrice + freightPrice
            shoppingCart.value?.freightPrice = freightPrice
        }
    }

    private fun calculateInitialPrice(amount: Int, price: Int): Int {
        return amount * price
    }

    private fun calculateFinalPrice(amount: Int, price: Int, discount: Int): Int {
        return amount * (price - discount)
    }

    fun onClickCheckout() {
        val data = JsonObject()
        data.addProperty("data", "carrinho de compras")
        checkout(data)
    }

    private fun checkout(data: JsonObject) {
        listener.showProgressDialogCheckout()
        repo.checkout(data, {
            listener.successCheckout()
            deleteAllItemsCart()
        }, { error ->
            error.printStackTrace()
            listener.failedCheckout()
        })
    }

    private fun deleteAllItemsCart() {
        viewModelScope.launch {
            repo.deleteAllItemsCart()
        }
    }

    override fun onCleared() {
        repo.clearCompositeDisposable()
        super.onCleared()
    }
}