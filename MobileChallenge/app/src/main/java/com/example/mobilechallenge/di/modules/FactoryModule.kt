package com.example.mobilechallenge.di.modules

import com.example.mobilechallenge.data.repositories.RepositoryImpl
import com.example.mobilechallenge.view.factory.DefaultFactory
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class FactoryModule {

    @Provides
    fun provideBannerFactory(repository: RepositoryImpl): DefaultFactory {
        return DefaultFactory(repository)
    }

}