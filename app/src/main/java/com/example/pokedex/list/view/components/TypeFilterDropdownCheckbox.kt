package com.example.pokedex.list.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pokedex.R

@Composable
fun TypeFilterDropdownCheckbox(
    availableTypes: List<String>,
    selectedTypes: List<String>,
    onTypeToggle: (String) -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedCount = selectedTypes.size

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded.value = !expanded.value }
        ) {
            val buttonText = when (selectedCount) {
                0 -> stringResource(R.string.pokemon_list_type_filter_count)
                1 -> selectedTypes.first().replaceFirstChar { it.uppercase() }
                else -> "${stringResource(R.string.pokemon_list_type_filter_count)} ($selectedCount)"
            }
            Text(buttonText)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = 500.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                if (selectedTypes.isNotEmpty()) {
                    TextButton(
                        onClick = onClearFilters,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Limpar", style = MaterialTheme.typography.labelMedium)
                    }
                }

                availableTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.replaceFirstChar { it.uppercase() }) },
                        onClick = { onTypeToggle(type) },
                        leadingIcon = {
                            Checkbox(
                                checked = selectedTypes.contains(type),
                                onCheckedChange = null
                            )
                        }
                    )
                }
            }
        }
    }
}





