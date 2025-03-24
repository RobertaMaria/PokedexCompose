package com.example.pokedex.list.view.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R

@Composable
fun SearchTextField(searchText: String, onSearchTextChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        label = { Text(stringResource(R.string.pokemon_list_search_hint)) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchTextChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clean),
                        contentDescription = stringResource(
                            R.string.pokemon_list_search_content_description
                        )
                    )
                }
            }
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}