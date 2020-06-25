package com.example.mobilechallenge.di.modules

import com.example.mobilechallenge.data.repositories.Repository
import com.example.mobilechallenge.view.factory.DefaultFactory
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class FactoryModule {

    @Provides
    fun provideBannerFactory(repository: Repository): DefaultFactory {
        return DefaultFactory(repository)
    }

}