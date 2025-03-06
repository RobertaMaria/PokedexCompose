package com.example.pokedex.list.data.mapper

import android.net.Uri
import com.example.pokedex.list.data.database.entity.RemoteKeysEntity
import com.example.pokedex.list.data.api.model.PokemonListResponse
import com.example.pokedex.list.data.database.entity.PokemonEntity
import com.example.pokedex.list.domain.model.PokemonList

class PokemonListMapper {
    fun mapToIdsPokemon(response: PokemonListResponse): List<Int> {
        return response.results.map {
            getIdFromUrl(url = it.url)
        }
    }

    fun mapToRemotesKeyEntity(response: PokemonListResponse): List<RemoteKeysEntity> {
        return response.results.map {
            RemoteKeysEntity(
                id = getIdFromUrl(it.url),
                prevKey = getOffsetParameter(response.previous),
                nextKey = getOffsetParameter(response.next)
            )
        }
    }

    private fun getOffsetParameter(url: String?): Int? {
        return url?.let {
            Uri.parse(url).getQueryParameter("offset")?.toInt()
        }
    }

    private fun getIdFromUrl(url: String) = url.split("/").dropLast(1).last().toInt()

    fun mapToDomain(pokemonEntity: PokemonEntity): PokemonList {
        return pokemonEntity.run {
            PokemonList(
                id = id,
                name = name,
                image = image,
                height = height,
                weight = weight,
                type = type
            )
        }
    }
}