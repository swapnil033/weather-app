package com.swapnil.weatherapp.presentation.weather_page.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherType
import com.swapnil.weatherapp.presentation.ui.theme.DeepBlue
import com.swapnil.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    isSelectedHour: Boolean = false,
    onHourSelect: (WeatherData) -> Unit,
) {

    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }

    val bgColor = if (isSelectedHour) DeepBlue else Color.Transparent

    Column(
        modifier = modifier
            .background(
                color = bgColor,
                shape = RoundedCornerShape(10.dp)
        )
            .padding(5.dp)
            .clickable {
                onHourSelect(weatherData)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime, color = Color.LightGray
        )
        Image(
            painter = painterResource(
                id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperatureCelsius}°C",
            color = textColor, fontWeight = FontWeight.Bold)
    }

}

@Preview
@Composable
private fun HourlyWeatherDisplayPreview() {
    WeatherAppTheme {
        val weather = WeatherData(
            time = LocalDateTime.now(),
            temperatureCelsius = 0.0,
            pressure = 0.0,
            humidity = 0.0,
            windSpeed = 0.0,
            weatherType = WeatherType.fromWMO(0)
        )

        HourlyWeatherDisplay(weather){}
    }
}