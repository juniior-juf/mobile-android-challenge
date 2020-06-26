package com.example.mobilechallenge.view.ui.shopping_cart

interface ShoppingCartListener {

    fun showProgressDialogCheckout()

    fun showDialogSuccessCheckout()

    fun successCheckout()

    fun failedCheckout()

}