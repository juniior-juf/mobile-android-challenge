package com.example.mobilechallenge.data.models

data class Game(
    val id: Int = 0,
    val title: String = "",
    val publisher: String = "",
    val image: String = "",
    val discount: Int = 0,
    val price: Int = 0,
    val description: String = "",
    val rating: Float = 0F,
    val stars: Int = 0,
    val reviews: Int = 0
)