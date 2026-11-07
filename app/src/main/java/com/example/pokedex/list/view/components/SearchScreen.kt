package com.example.pokedex.list.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.pokedex.common.model.TypeColoursEnum
import com.example.pokedex.list.view.model.PokemonListUi

@Composable
fun SearchScreen(
    pokemonLazyPagingItems: LazyPagingItems<PokemonListUi>,
    onSearchTextChange: (String) -> Unit,
    searchText: String,
    modifier: Modifier = Modifier,
    onClickPokemon: (id: Int) -> Unit = {},
    selectedTypes: List<String> = emptyList(),
    availableTypes: List<String> = emptyList(),
    onTypeToggle: (String) -> Unit = {},
    onClearFilters: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val coroutineScope = rememberCoroutineScope()

    var previousSearchText by rememberSaveable { mutableStateOf(searchText) }
    var previousSelectedTypes by rememberSaveable { mutableStateOf(selectedTypes.joinToString(",")) }
    var shouldScrollToTop by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(searchText, selectedTypes) {
        val currentTypesString = selectedTypes.joinToString(",")
        if (searchText != previousSearchText || currentTypesString != previousSelectedTypes) {
            previousSearchText = searchText
            previousSelectedTypes = currentTypesString
            shouldScrollToTop = true
            listState.scrollToItem(0)
        }
    }

    LaunchedEffect(pokemonLazyPagingItems.loadState.refresh) {
        if (shouldScrollToTop && pokemonLazyPagingItems.loadState.refresh is LoadState.NotLoading) {
            listState.scrollToItem(0)
            shouldScrollToTop = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SearchTextField(
                searchText = searchText,
                onSearchTextChange = onSearchTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 6.dp)
            )

            // 2. Barra de Chips horizontais roláveis para os Tipos
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Chip "Limpar" mantido sempre fixo na primeira posição
                item {
                    val hasFilters = selectedTypes.isNotEmpty()
                    val clearContainerColor = if (hasFilters) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceContainerLow
                    }
                    val clearContentColor = if (hasFilters) {
                        MaterialTheme.colorScheme.onErrorContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    }

                    Surface(
                        modifier = Modifier.clickable(enabled = hasFilters) { onClearFilters() },
                        shape = RoundedCornerShape(16.dp),
                        color = clearContainerColor,
                        contentColor = clearContentColor
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpar filtros",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (hasFilters) "Limpar (${selectedTypes.size})" else "Limpar",
                                fontSize = 13.sp,
                                fontWeight = if (hasFilters) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                items(availableTypes) { typeString ->
                    val isSelected = selectedTypes.contains(typeString)
                    val typeEnum = remember(typeString) {
                        TypeColoursEnum.entries.firstOrNull { it.typeName.equals(typeString, ignoreCase = true) }
                    }

                    val containerColor = if (isSelected && typeEnum != null) {
                        typeEnum.getColor()
                    } else if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceContainerHigh
                    }

                    val contentColor = if (isSelected) {
                        Color.White
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }

                    Surface(
                        modifier = Modifier.clickable { onTypeToggle(typeString) },
                        shape = RoundedCornerShape(16.dp),
                        color = containerColor,
                        contentColor = contentColor,
                        shadowElevation = if (isSelected) 4.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            typeEnum?.let {
                                Image(
                                    painter = painterResource(id = it.getDrawableId()),
                                    contentDescription = it.getFormattedName(),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = typeEnum?.getFormattedName() ?: typeString,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // 3. Lista de Pokémons filtrados
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = pokemonLazyPagingItems.itemCount,
                    key = pokemonLazyPagingItems.itemKey { it.id }
                ) { index ->
                    pokemonLazyPagingItems[index]?.let { pokemon ->
                        PokemonItem(pokemon = pokemon, onClickPokemon = onClickPokemon)
                    }
                }
            }
        }

        LoadStateFooter(
            pokemonLazyPagingItems.loadState,
            pokemonLazyPagingItems.itemCount,
            modifier = Modifier.align(Alignment.Center)
        )
        ScrollToTopButton(
            listState = listState,
            showButton = showButton,
            coroutineScope = coroutineScope,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp)
        )
    }
}
