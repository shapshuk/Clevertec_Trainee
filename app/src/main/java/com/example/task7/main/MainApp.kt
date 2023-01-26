package com.example.task7.main

import android.app.Application
import android.content.Context
import com.example.task7.dagger.AppComponent
import com.example.task7.dagger.DaggerAppComponent

class MainApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }