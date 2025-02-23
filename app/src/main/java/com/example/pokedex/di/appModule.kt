package com.example.pokedex.di

import com.example.pokedex.list.viewmodel.PokemonListViewModel
import com.example.pokedex.data.mediator.PokemonRemoteMediator
import com.example.pokedex.data.database.AppDataBase
import com.example.pokedex.data.datasource.local.PokemonLocalDataSource
import com.example.pokedex.data.datasource.local.PokemonLocalDataSourceImp
import com.example.pokedex.data.datasource.remote.PokemonRemoteDataSource
import com.example.pokedex.data.datasource.remote.PokemonRemoteDataSourceImp
import com.example.pokedex.data.mapper.PokemonMapper
import com.example.pokedex.data.api.RetrofitConfig
import com.example.pokedex.data.api.service.PokemonService
import com.example.pokedex.domain.repository.ListPokemonRepository
import com.example.pokedex.data.repository.ListPokemonRepositoryImpl
import com.example.pokedex.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.factory.PokemonListFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    // Database
    single { AppDataBase.getDatabase(androidContext()) }
    factory { get<AppDataBase>().pokemonDao() }
    factory { get<AppDataBase>().remoteKeysDao() }

    // Network
    single { RetrofitConfig.instance }
    single { get<Retrofit>().create(PokemonService::class.java) }

    // Data Sources
    factory<PokemonRemoteDataSource> { PokemonRemoteDataSourceImp(get()) }
    factory<PokemonLocalDataSource> { PokemonLocalDataSourceImp(get(), get()) }

    // Mappers
    factory { PokemonMapper() }

    // Repositories
    single<ListPokemonRepository> {
        ListPokemonRepositoryImpl(
            pokemonRemoteMediator = get(),
            pokemonDao = get(),
            mapper = get()
        )
    }

    // Use Cases
    factory { GetPokemonListUseCase(get()) }

    // Remote Mediator
    factory { PokemonRemoteMediator(get(), get(), get()) }

    // Factories
    factory { PokemonListFactory(androidContext()) }

    // ViewModels
    viewModel { PokemonListViewModel(get(), get()) }
}