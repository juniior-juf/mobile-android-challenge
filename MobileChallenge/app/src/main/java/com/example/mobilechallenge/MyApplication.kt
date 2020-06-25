package com.example.mobilechallenge

import android.app.Application
import com.example.mobilechallenge.di.components.ApplicationComponent
import com.example.mobilechallenge.di.components.DaggerApplicationComponent
import com.example.mobilechallenge.di.modules.ApplicationModule
import com.example.mobilechallenge.di.modules.DatabaseModule

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .databaseModule(DatabaseModule(this))
            .build()

        component.inject(this)
    }

    companion object {
        private lateinit var component: ApplicationComponent

        fun getAppComponent() = component
    }
}