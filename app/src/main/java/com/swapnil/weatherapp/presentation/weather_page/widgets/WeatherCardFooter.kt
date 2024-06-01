package com.swapnil.weatherapp.presentation.weather_page.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swapnil.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun WeatherCardFooter(
    value: Int,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(color = Color.White),
    iconTint: Color = Color.White
) {

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(25.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "$value$unit", style = textStyle)
    }

}

@Preview
@Composable
private fun WeatherCardFooterPreview() {
    WeatherAppTheme {
        WeatherCardFooter(
            value = 0, unit = "KG",
            icon = Icons.Default.Call,
            textStyle = TextStyle(color = Color.White),
        )
    }
}