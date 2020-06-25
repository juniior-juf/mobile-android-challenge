package com.example.mobilechallenge

import android.app.Application
import com.example.mobilechallenge.di.components.ApplicationComponent
import com.example.mobilechallenge.di.components.DaggerApplicationComponent

class MyApplication : Application() {

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder().build()
    }

    fun getAppComponent() = component
}