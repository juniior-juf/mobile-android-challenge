package com.example.mobilechallenge.di.modules

import com.example.mobilechallenge.data.local.dao.ItemCartDao
import com.example.mobilechallenge.data.remote.Api
import com.example.mobilechallenge.data.repositories.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class RepositoryModule {

    @Provides
    fun provideRepository(api: Api, dao: ItemCartDao): RepositoryImpl {
        return RepositoryImpl(api, dao)
    }
}