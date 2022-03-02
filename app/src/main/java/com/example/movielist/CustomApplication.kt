package com.example.movielist

import android.app.Application
import com.example.movielist.models.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(module)
        }
    }
}