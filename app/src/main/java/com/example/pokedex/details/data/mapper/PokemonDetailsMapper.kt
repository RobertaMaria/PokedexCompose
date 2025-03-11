package com.example.pokedex.details.data.mapper

import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.details.data.api.model.ChainResponse
import com.example.pokedex.details.data.api.model.EvolutionsResponse
import com.example.pokedex.details.data.api.model.PokemonSpecieResponse
import com.example.pokedex.details.data.database.entity.EvolutionsEntity
import com.example.pokedex.details.data.database.entity.PokemonEvolutionEntity
import com.example.pokedex.details.data.database.entity.PokemonSpecieEntity
import com.example.pokedex.details.domain.model.Evolutions
import com.example.pokedex.details.domain.model.PokemonDetails
import com.example.pokedex.details.domain.model.PokemonStats
import com.example.pokedex.details.domain.model.Stat

private const val LANGUAGE_ENGLISH = "en"

class PokemonDetailsMapper {

    fun mapToSpecieEntity(
        response: PokemonSpecieResponse,
        id: Int,
        damageRelations: Pair<List<String>, List<String>>
    ): PokemonSpecieEntity {
        val flavorTextEntry = response.flavorTextEntries.firstOrNull {
            it.language.name == LANGUAGE_ENGLISH
        }?.flavorText.orEmpty()

        val evolutionId = extractIdFromUrl(response.chainResponse.url)

        return response.run {
            PokemonSpecieEntity(
                id = id,
                description = flavorTextEntry,
                evolutionId = evolutionId,
                doubleDamage = damageRelations.first,
                noDamage = damageRelations.second
            )
        }
    }

    fun mapToDomain(
        specieEntity: PokemonSpecieEntity,
        pokemonEntity: PokemonEntity,
        evolutionEntity: PokemonEvolutionEntity
    ): PokemonDetails {
        return PokemonDetails(
            id = specieEntity.id,
            description = specieEntity.description,
            name = pokemonEntity.name,
            image = pokemonEntity.image,
            type = pokemonEntity.type,
            stats = pokemonEntity.stats.map {
                PokemonStats(baseStat = it.baseStat, stat = Stat(it.stat.name))
            },
            evolutions = evolutionEntity.evolutions.map {
                Evolutions(name = it.name, pokemonId = it.pokemonId)
            },
            doubleDamage = specieEntity.doubleDamage,
            noDamage = specieEntity.noDamage
        )
    }

    fun mapperToEvolutionsEntity(
        chainResponse: ChainResponse,
        url: String
    ): PokemonEvolutionEntity {

        val evolutions = extractSpecies(chainResponse)
        val evolutionId = extractIdFromUrl(url)

        val evolutionsEntity = evolutions.map { evolution ->
            val pokemonId = extractIdFromUrl(evolution.url)
            EvolutionsEntity(
                pokemonId = pokemonId,
                name = evolution.name
            )
        }

        return PokemonEvolutionEntity(evolutions = evolutionsEntity, evolutionId = evolutionId)
    }

    private fun extractSpecies(chainResponse: ChainResponse): List<EvolutionsResponse> {
        val evolutionsList = mutableListOf<EvolutionsResponse>()

        fun traverse(currentChain: ChainResponse) {
            evolutionsList.add(currentChain.evolutionsResponse)
            currentChain.evolvesTo.forEach {
                traverse(it)
            }
        }

        traverse(chainResponse)
        return evolutionsList
    }

    private fun extractIdFromUrl(url: String): Int {
        val removeEndBar = url.substringBeforeLast("/")
        val idString = removeEndBar.substringAfterLast("/")
        return idString.toInt()
    }
}