package com.example.pokedex.details.view.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.pokedex.details.view.components.DetailsScreen
import com.example.pokedex.details.view.components.ErrorDialog
import com.example.pokedex.details.view.viewmodel.PokemonDetailsUiState
import com.example.pokedex.details.view.viewmodel.PokemonDetailsViewModel
import com.example.pokedex.list.view.components.LoadingAnimation
import com.example.pokedex.ui.theme.PokedexTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val DETAILS_ACTIVITY_POKEMON_ID = "pokemon_id"

class PokemonDetailsActivity : ComponentActivity() {

    private val viewModel: PokemonDetailsViewModel by viewModel {
        parametersOf(intent.getIntExtra(DETAILS_ACTIVITY_POKEMON_ID, 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                Surface {
                    PokemonDetailsScreen()
                }
            }
        }
    }

    @Composable
    fun PokemonDetailsScreen() {
        val uiState by viewModel.uiState.collectAsState()

        when (uiState) {
            is PokemonDetailsUiState.Success -> {
                DetailsScreen(
                    pokemonDetailsUi = (uiState as PokemonDetailsUiState.Success).pokemonDetailsUi,
                    onClickEvolution = { id ->
                        viewModel.onEvolutionClicked(id)
                    })
            }

            is PokemonDetailsUiState.Loading -> {
                LoadingAnimation(isCentered = true)
            }

            is PokemonDetailsUiState.Error -> {
                ErrorDialog(onDismiss = { finish() })
            }
        }
    }

    companion object {
        fun newInstance(context: Context, id: Int): Intent {
            val intent = Intent(context, PokemonDetailsActivity::class.java).apply {
                putExtra(DETAILS_ACTIVITY_POKEMON_ID, id)
            }
            return intent
        }
    }
}