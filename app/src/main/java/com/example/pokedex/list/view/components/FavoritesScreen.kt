package com.example.pokedex.list.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.list.view.model.PokemonListUi

@Composable
fun FavoritesScreen(
    favoritePokemons: List<PokemonListUi>,
    onClickPokemon: (id: Int) -> Unit = {},
    onRemoveFavorite: (id: Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (favoritePokemons.isEmpty()) {
            Text(
                text = stringResource(R.string.favorites_empty_message),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = favoritePokemons,
                    key = { it.id }
                ) { pokemon ->
                    PokemonItem(
                        pokemon = pokemon,
                        onClickPokemon = onClickPokemon,
                        onRemoveFavorite = onRemoveFavorite
                    )
                }
            }
        }
    }
}
