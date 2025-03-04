package com.example.pokedex.di

import com.example.pokedex.data.api.RetrofitConfig
import com.example.pokedex.data.api.service.PokemonService
import com.example.pokedex.data.database.AppDataBase
import com.example.pokedex.data.datasource.local.PokemonLocalDataSource
import com.example.pokedex.data.datasource.local.PokemonLocalDataSourceImp
import com.example.pokedex.data.datasource.remote.PokemonRemoteDataSource
import com.example.pokedex.data.datasource.remote.PokemonRemoteDataSourceImp
import com.example.pokedex.data.mapper.PokemonMapper
import com.example.pokedex.data.mediator.PokemonRemoteMediator
import com.example.pokedex.data.repository.ListPokemonRepositoryImpl
import com.example.pokedex.details.data.api.service.PokemonDetailsService
import com.example.pokedex.details.data.datasource.local.PokemonDetailLocalDataSource
import com.example.pokedex.details.data.datasource.local.PokemonDetailLocalDataSourceImp
import com.example.pokedex.details.data.datasource.remote.PokemonDetailRemoteDataSource
import com.example.pokedex.details.data.datasource.remote.PokemonDetailRemoteDataSourceImp
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.details.data.repository.PokemonDetailsRepositoryImp
import com.example.pokedex.details.domain.repository.PokemonDetailsRepository
import com.example.pokedex.details.domain.usecase.GetPokemonDetailsUseCase
import com.example.pokedex.details.view.factory.PokemonDetailsFactory
import com.example.pokedex.details.view.viewmodel.PokemonDetailsViewModel
import com.example.pokedex.domain.repository.ListPokemonRepository
import com.example.pokedex.domain.usecase.GetPokemonListUseCase
import com.example.pokedex.list.factory.PokemonListFactory
import com.example.pokedex.list.viewmodel.PokemonListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {

    // Database
    single { AppDataBase.getDatabase(androidContext()) }
    factory { get<AppDataBase>().pokemonDao() }
    factory { get<AppDataBase>().remoteKeysDao() }
    factory { get<AppDataBase>().pokemonDetailsDao() }
    factory { get<AppDataBase>().pokemonEvolutionDao() }

    // Network
    single { RetrofitConfig.instance }
    single { get<Retrofit>().create(PokemonService::class.java) }
    single { get<Retrofit>().create(PokemonDetailsService::class.java) }

    // Data Sources
    factory<PokemonRemoteDataSource> { PokemonRemoteDataSourceImp(get()) }
    factory<PokemonLocalDataSource> { PokemonLocalDataSourceImp(get(), get()) }
    factory<PokemonDetailLocalDataSource> { PokemonDetailLocalDataSourceImp(get(), get(), get()) }
    factory<PokemonDetailRemoteDataSource> { PokemonDetailRemoteDataSourceImp(get()) }

    // Mappers
    factory { PokemonMapper() }
    factory { PokemonDetailsMapper() }

    // Repositories
    single<ListPokemonRepository> {
        ListPokemonRepositoryImpl(
            pokemonRemoteMediator = get(),
            pokemonDao = get(),
            mapper = get()
        )
    }

    single<PokemonDetailsRepository> {
        PokemonDetailsRepositoryImp(get(), get(), get())
    }

    // Use Cases
    factory { GetPokemonListUseCase(get()) }
    factory { GetPokemonDetailsUseCase(get()) }

    // Remote Mediator
    factory { PokemonRemoteMediator(get(), get(), get()) }

    // Factories
    factory { PokemonListFactory(androidContext()) }
    factory { PokemonDetailsFactory() }

    // ViewModels
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { (id: Int) ->
        PokemonDetailsViewModel(
            id = id, useCase = get(), factory = get()
        )
    }
}