package com.example.mobilechallenge.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items_cart_table")
class ItemCart(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "price") val price: Int = 0,
    @ColumnInfo(name = "discount") val discount: Int = 0,
    @ColumnInfo(name = "amount") val amount: Int = 0
)