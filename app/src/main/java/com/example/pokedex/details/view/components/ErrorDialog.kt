package com.example.pokedex.details.view.components

import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.pokedex.R

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.pokebola),
                contentDescription = ""
            )
        },
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.pokemon_details_error_title)) },
        text = { Text(stringResource(R.string.pokemon_details_error_subtitle)) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.pokemon_details_button_title))
            }
        }
    )
}