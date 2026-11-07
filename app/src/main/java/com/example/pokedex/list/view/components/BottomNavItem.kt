package com.example.pokedex.list.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokedex.R

sealed class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = "home",
        titleResId = R.string.bottom_nav_home,
        icon = Icons.Default.Home
    )

    object Search : BottomNavItem(
        route = "search",
        titleResId = R.string.bottom_nav_search,
        icon = Icons.Default.Search
    )

    object Favorites : BottomNavItem(
        route = "favorites",
        titleResId = R.string.bottom_nav_favorites,
        icon = Icons.Default.Favorite
    )
}
