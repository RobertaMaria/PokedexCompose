package com.example.pokedex.list.view.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokedex.details.view.screen.PokemonDetailsActivity
import com.example.pokedex.list.view.components.HomeScreen
import com.example.pokedex.list.viewmodel.PokemonListViewModel
import com.example.pokedex.ui.theme.PokedexTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PokemonListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(viewModel = getViewModel())
                }
            }
        }
    }
}

@Composable
fun App(viewModel: PokemonListViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        pokemonLazyPagingItems = uiState.list.collectAsLazyPagingItems(),
        onSearchTextChange = { viewModel.setSearchText(it) },
        searchText = uiState.searchText,
        onClickPokemon = { id ->
            val intent = PokemonDetailsActivity.newInstance(context, id)
            context.startActivity(intent)
        })
}