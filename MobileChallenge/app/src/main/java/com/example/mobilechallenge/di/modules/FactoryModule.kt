package com.example.mobilechallenge.di.modules

import com.example.mobilechallenge.data.remote.Api
import com.example.mobilechallenge.data.repositories.BannerRepository
import com.example.mobilechallenge.view.factory.MainFactory
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class FactoryModule {

    @Provides
    fun provideBannerFactory(repository: BannerRepository): MainFactory {
        return MainFactory(repository)
    }

}