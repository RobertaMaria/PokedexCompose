package com.example.pokedex.details.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedex.utils.duplicateIfSingle

@Composable
fun CustomProgressBar(
    baseStat: Int,
    colors: List<Color>
) {
    val widthBar = rememberSaveable { 200 }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .height(20.dp)
            .width(widthBar.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(color = Color.Gray, cornerRadius = CornerRadius(15.dp.toPx()))

            drawRoundRect(
                brush = Brush.horizontalGradient(colors.duplicateIfSingle()),
                cornerRadius = CornerRadius(15.dp.toPx()),
                size = Size(widthBar.dp.toPx() * baseStat / 100, size.height)
            )
        }

        Text(
            text = "$baseStat",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}