package com.example.mobilechallenge.di.components

import android.content.Context
import com.example.mobilechallenge.MyApplication
import com.example.mobilechallenge.di.modules.ApplicationModule
import com.example.mobilechallenge.di.modules.FactoryModule
import com.example.mobilechallenge.view.ui.detail.DetailActivity
import com.example.mobilechallenge.view.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, FactoryModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun application(): MyApplication

    fun inject(activity: MainActivity)

    fun inject(activity: DetailActivity)

}