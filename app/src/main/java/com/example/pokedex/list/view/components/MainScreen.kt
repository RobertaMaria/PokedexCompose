package com.example.pokedex.list.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.pokedex.list.view.model.PokemonListUi

@Composable
fun MainScreen(
    homeLazyPagingItems: LazyPagingItems<PokemonListUi>,
    searchLazyPagingItems: LazyPagingItems<PokemonListUi>,
    onSearchTextChange: (String) -> Unit,
    searchText: String,
    onClickPokemon: (id: Int) -> Unit = {},
    selectedTypes: List<String> = emptyList(),
    availableTypes: List<String> = emptyList(),
    favoritePokemons: List<PokemonListUi> = emptyList(),
    onRemoveFavorite: (id: Int) -> Unit = {},
    onTypeToggle: (String) -> Unit = {},
    onClearFilters: () -> Unit = {}
) {
    var selectedTabRoute by rememberSaveable { mutableStateOf(BottomNavItem.Home.route) }
    val items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Favorites)
    val selectedTab = items.find { it.route == selectedTabRoute } ?: BottomNavItem.Home

    Box(modifier = Modifier.fillMaxSize()) {
        when (selectedTab) {
            BottomNavItem.Home -> {
                HomeScreen(
                    pokemonLazyPagingItems = homeLazyPagingItems,
                    onClickPokemon = onClickPokemon,
                    modifier = Modifier.fillMaxSize()
                )
            }
            BottomNavItem.Search -> {
                SearchScreen(
                    pokemonLazyPagingItems = searchLazyPagingItems,
                    onSearchTextChange = onSearchTextChange,
                    searchText = searchText,
                    modifier = Modifier.fillMaxSize(),
                    onClickPokemon = onClickPokemon,
                    selectedTypes = selectedTypes,
                    availableTypes = availableTypes,
                    onTypeToggle = onTypeToggle,
                    onClearFilters = onClearFilters
                )
            }
            BottomNavItem.Favorites -> {
                FavoritesScreen(
                    favoritePokemons = favoritePokemons,
                    onClickPokemon = onClickPokemon,
                    onRemoveFavorite = onRemoveFavorite,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .height(64.dp)
                .fillMaxWidth(),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.95f),
            shadowElevation = 8.dp,
            tonalElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = selectedTab == item
                    val iconColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    }

                    Column(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { selectedTabRoute = item.route }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.titleResId),
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = stringResource(id = item.titleResId),
                            color = iconColor,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}
