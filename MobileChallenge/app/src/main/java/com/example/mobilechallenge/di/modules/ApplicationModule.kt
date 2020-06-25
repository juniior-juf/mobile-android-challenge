package com.example.mobilechallenge.di.modules

import android.content.Context
import androidx.annotation.NonNull
import com.example.mobilechallenge.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(@NonNull private val application: MyApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideApplication(): MyApplication {
        return application
    }
}