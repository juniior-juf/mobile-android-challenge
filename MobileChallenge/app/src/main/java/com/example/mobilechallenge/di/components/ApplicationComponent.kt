package com.example.mobilechallenge.di.components

import com.example.mobilechallenge.di.modules.FactoryModule
import com.example.mobilechallenge.view.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FactoryModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

}