package com.example.mobilechallenge.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items_cart_table")
class ItemCart(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "discount") val discount: Int,
    @ColumnInfo(name = "amount") val amount: Int
)