package com.example.mobilechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilechallenge.data.local.dao.ItemCartDao
import com.example.mobilechallenge.data.models.ItemCart

@Database(entities = [ItemCart::class], version = 1, exportSchema = false)
abstract class DatabaseLocal : RoomDatabase() {

    abstract fun itemCartDao(): ItemCartDao
}