package com.example.mobilechallenge.di.modules

import com.example.mobilechallenge.data.remote.Api
import com.example.mobilechallenge.data.repositories.BannerRepository
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    fun provideBannerRepository(api: Api): BannerRepository {
        return BannerRepository(api)
    }

}