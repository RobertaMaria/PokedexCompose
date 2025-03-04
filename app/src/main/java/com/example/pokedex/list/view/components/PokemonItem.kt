package com.example.pokedex.list.view.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.R
import com.example.pokedex.list.factory.PokemonMeasureData
import com.example.pokedex.list.view.model.PokemonUi
import com.example.pokedex.list.view.model.TypeColoursEnum
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.utils.formatPokemonId

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonItem(
    pokemon: PokemonUi,
    onClickPokemon: (id: Int) -> Unit = {}
) {
    Card(
        modifier = Modifier.clickable { onClickPokemon(pokemon.id) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        pokemon.color.map {
                            it.getColor()
                        }
                            .plus(Color.Transparent)
                    )
                )
                .padding(8.dp)
                .heightIn(min = 150.dp, max = 180.dp)
        ) {

            val (image, id, name, types, listOfMeasurements) = createRefs()

            Image(
                modifier = Modifier
                    .size(150.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    model = pokemon.image,
                    error = painterResource(R.drawable.pokebola),
                    placeholder = painterResource(R.drawable.pokebola)
                ),
                contentDescription = pokemon.name
            )

            Text(
                text = pokemon.id.formatPokemonId(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(id) {
                        top.linkTo(image.bottom)
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                    }
            )

            Text(
                text = pokemon.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 8.dp)
            )

            FlowRow(
                modifier = Modifier
                    .constrainAs(types) {
                        top.linkTo(name.bottom)
                        start.linkTo(name.start)
                        end.linkTo(name.end)
                    }
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp, CenterHorizontally)
            ) {
                pokemon.color.forEach { type ->
                    PokemonType(type)
                }
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 16.dp, end = 4.dp)
                    .constrainAs(listOfMeasurements) {
                        top.linkTo(types.bottom)
                        start.linkTo(types.start)
                        end.linkTo(types.end)
                    }

            ) {

                pokemon.measuremList.forEach { (measureFormatted, descriptionId, iconId) ->
                    PokemonMeasure(
                        measureFormatted = measureFormatted,
                        iconTitle = stringResource(descriptionId),
                        iconId = iconId
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PokemonItemPreview() {
    PokedexTheme {
        Surface {
            PokemonItem(
                PokemonUi(
                    id = 10,
                    name = "Pikachu",
                    image = "",
                    measuremList = listOf(
                        PokemonMeasureData(
                            measureFormatted = "100,Kg",
                            descriptionId = R.string.pokemon_list_weight,
                            iconId = R.drawable.weight_kilogram
                        ),
                        PokemonMeasureData(
                            measureFormatted = "2,0m",
                            descriptionId = R.string.pokemon_list_Height,
                            iconId = R.drawable.ruler_square
                        )
                    ),
                    color = listOf(
                        TypeColoursEnum.DRAGON,
                        TypeColoursEnum.ELECTRIC
                    )
                )
            )
        }
    }
}