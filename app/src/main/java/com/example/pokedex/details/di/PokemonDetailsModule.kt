package com.example.pokedex.details.di

import com.example.pokedex.common.database.AppDataBase
import com.example.pokedex.details.data.api.service.PokemonDetailsService
import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.example.pokedex.details.data.datasource.local.PokemonDetailsLocalDataSourceImp
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.example.pokedex.details.data.datasource.remote.PokemonDetailsRemoteDataSourceImp
import com.example.pokedex.details.data.mapper.PokemonDetailsMapper
import com.example.pokedex.details.data.repository.PokemonDetailsRepositoryImp
import com.example.pokedex.details.domain.repository.PokemonDetailsRepository
import com.example.pokedex.details.domain.usecase.GetPokemonDetailsUseCase
import com.example.pokedex.details.view.factory.PokemonDetailsFactory
import com.example.pokedex.details.view.viewmodel.PokemonDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val detailsModule = module {
    // Database
    factory { get<AppDataBase>().pokemonSpecieDao() }
    factory { get<AppDataBase>().pokemonEvolutionDao() }

    // Network
    single { get<Retrofit>().create(PokemonDetailsService::class.java) }

    // Data Sources
    factory<PokemonDetailsLocalDataSource> { PokemonDetailsLocalDataSourceImp(get(), get(), get()) }
    factory<PokemonDetailsRemoteDataSource> { PokemonDetailsRemoteDataSourceImp(get()) }

    // Mappers
    factory { PokemonDetailsMapper() }

    // Repositories
    single<PokemonDetailsRepository> {
        PokemonDetailsRepositoryImp(get(), get(), get())
    }
    // Use Cases

    factory { GetPokemonDetailsUseCase(get()) }

    // Factories
    factory { PokemonDetailsFactory(androidContext()) }

    // ViewModels
    viewModel { (id: Int) ->
        PokemonDetailsViewModel(
            id = id, useCase = get(), factory = get()
        )
    }
}