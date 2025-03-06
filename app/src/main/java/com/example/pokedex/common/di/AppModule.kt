package com.example.pokedex.common.di

import com.example.pokedex.common.database.AppDataBase
import com.example.pokedex.common.retrofit.RetrofitConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    // Database
    single { AppDataBase.getDatabase(androidContext()) }

    // Network
    single { RetrofitConfig.instance }
}