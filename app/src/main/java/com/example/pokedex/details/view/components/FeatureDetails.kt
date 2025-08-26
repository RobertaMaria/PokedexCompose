package com.example.pokedex.details.view.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.R
import com.example.pokedex.details.view.model.FeaturesUi
import com.example.pokedex.ui.theme.PokedexTheme


@Composable
fun FeatureDetails(featuresUi: FeaturesUi, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.testTag("feature_details_column")
    )
    {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            Column {
                InfoRow(
                    title = stringResource(R.string.pokemon_features_height_title),
                    value = featuresUi.height,
                    valueTestTag = "feature_height_text"
                )
                InfoRow(
                    title = stringResource(R.string.pokemon_features_weight_title),
                    value = featuresUi.weight,
                    valueTestTag = "feature_weight_text"
                )
            }
            Column {
                InfoDrawableRow(
                    title = stringResource(R.string.pokemon_features_gender_title),
                    value = featuresUi.gender
                )
                InfoRow(
                    title = stringResource(R.string.pokemon_features_category_title),
                    value = featuresUi.category,
                    valueTestTag = "feature_category_text"
                )
            }
        }
        InfoListRow(
            title = stringResource(R.string.pokemon_features_ability_title),
            value = featuresUi.ability,
            modifier = Modifier.testTag("feature_abilities_text")
        )
    }
}

@Composable
fun InfoRow(title: String, value: String, modifier: Modifier = Modifier, valueTestTag: String? = null) {
    Row(modifier = modifier) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            modifier = valueTestTag?.let { Modifier.testTag(it) } ?: Modifier
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun InfoDrawableRow(title: String, @DrawableRes value: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.testTag("feature_drawable_text")) {
        Text(text = title, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Image(painter = painterResource(id = value), contentDescription = title)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoListRow(title: String, value: List<String>, modifier: Modifier = Modifier) {
    FlowRow(modifier = modifier) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        value.forEach { item ->
            Text(
                text = item,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FeatureDetailsPreview() {
    PokedexTheme {
        Surface {
            FeatureDetails(
                featuresUi = FeaturesUi(
                    height = "0.7 m",
                    weight = "6.9 kg",
                    gender = R.drawable.ic_female_male,
                    category = "Seed Pokémon",
                    ability = listOf("Overgrow", "Chlorophyll")
                )
            )
        }
    }
}