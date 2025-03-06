package com.example.pokedex.list.di

import com.example.pokedex.common.database.AppDataBase
import com.example.pokedex.list.data.api.service.PokemonListService
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSource
import com.example.pokedex.list.data.datasource.local.PokemonListLocalDataSourceImp
import com.example.pokedex.list.data.datasource.remote.PokemonListRemoteDataSource
import com.example.pokedex.list.data.datasource.remote.PokemonListRemoteDataSourceImp
import com.example.pokedex.list.data.mapper.PokemonListMapper
import com.example.pokedex.list.data.mediator.PokemonListRemoteMediator
import com.example.pokedex.list.data.repository.PokemonListRepositoryImpl
import com.example.pokedex.list.domain.repository.PokemonListRepository
import com.example.pokedex.list.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.view.factory.PokemonListFactory
import com.example.pokedex.list.viewmodel.PokemonListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val listModule = module {
    // Database
    factory { get<AppDataBase>().pokemonDao() }
    factory { get<AppDataBase>().remoteKeysDao() }

    // Network
    single { get<Retrofit>().create(PokemonListService::class.java) }

    // Data Sources
    factory<PokemonListRemoteDataSource> { PokemonListRemoteDataSourceImp(get()) }
    factory<PokemonListLocalDataSource> { PokemonListLocalDataSourceImp(get(), get()) }

    // Mappers
    factory { PokemonListMapper() }

    // Repositories
    single<PokemonListRepository> {
        PokemonListRepositoryImpl(
            pokemonListRemoteMediator = get(),
            pokemonDao = get(),
            mapper = get()
        )
    }

    // Use Cases
    factory { GetPokemonListUseCase(get()) }

    // Remote Mediator
    factory { PokemonListRemoteMediator(get(), get(), get()) }

    // Factories
    factory { PokemonListFactory(androidContext()) }

    // ViewModels
    viewModel { PokemonListViewModel(get(), get()) }
}