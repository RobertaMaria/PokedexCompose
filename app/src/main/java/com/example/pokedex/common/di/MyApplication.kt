package com.example.pokedex.common.di

import android.app.Application
import com.example.pokedex.details.di.detailsModule
import com.example.pokedex.list.di.listModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule, detailsModule, listModule)
        }
    }
}