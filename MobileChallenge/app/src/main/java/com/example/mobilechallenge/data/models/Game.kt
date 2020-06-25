package com.example.mobilechallenge.data.models

data class Game(
    val id: Int,
    val title: String,
    val publisher: String,
    val image: String,
    val discount: Int,
    val price: Int,
    val description: String,
    val rating: Float,
    val stars: Int,
    val reviews: Int
)