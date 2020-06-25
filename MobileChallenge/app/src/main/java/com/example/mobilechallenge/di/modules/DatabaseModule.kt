package com.example.mobilechallenge.di.modules

import androidx.annotation.NonNull
import androidx.room.Room
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.data.local.DatabaseLocal
import com.example.mobilechallenge.data.local.dao.ItemCartDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(@NonNull application: MyApplication) {

    companion object {
        private const val DB_NAME = "mobile_challenge_db"
    }

    private val db: DatabaseLocal = Room.databaseBuilder(
        application.applicationContext,
        DatabaseLocal::class.java,
        DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDatabaseLocal(): DatabaseLocal {
        return db
    }

    @Singleton
    @Provides
    fun provideItemCartDao(db: DatabaseLocal): ItemCartDao {
        return db.itemCartDao()
    }
}