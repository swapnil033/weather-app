package com.swapnil.weatherapp.presentation.weather_page.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherType
import com.swapnil.weatherapp.domain.weather.WeatherWeekDayData
import com.swapnil.weatherapp.presentation.ui.theme.DeepBlue
import com.swapnil.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDateTime

@Composable
fun WeekDisplay(
    weatherInfo: List<WeatherWeekDayData>,
    modifier: Modifier = Modifier,
    onDaySelect: (WeatherWeekDayData) -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        for (day in weatherInfo) {

            val bgColor = if (day.isSelected) Color.White.copy(alpha = 0.8f) else Color.Transparent
            val textColor = if (day.isSelected) DeepBlue else Color.White

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = bgColor, shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onDaySelect(day)
                    }) {

                val f: NumberFormat = DecimalFormat("00")
                val dayOfMonth = f.format(day.dateTime.dayOfMonth)

                Text(
                    text = dayOfMonth,
                    color = textColor,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = day.day,
                    color = textColor.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeekDisplayPreview() {
    WeatherAppTheme {
        val weather = WeatherData(
            time = LocalDateTime.now(),
            temperatureCelsius = 0.0,
            pressure = 0.0,
            humidity = 0.0,
            windSpeed = 0.0,
            weatherType = WeatherType.fromWMO(0)
        )

        val weatherMap: List<WeatherData> = listOf(
            weather, weather, weather, weather, weather, weather, weather
        )

        val weatherWeekDayData1 = WeatherWeekDayData(
            dateTime = LocalDateTime.now(),
            day = "Thus",
            weatherData = weatherMap,
            isSelected = true,
        )

        val weatherWeekDayData = WeatherWeekDayData(
            dateTime = LocalDateTime.now(),
            day = "Thus",
            weatherData = weatherMap,
            isSelected = false,
        )

        val weatherInfo = listOf(
            weatherWeekDayData1,
            weatherWeekDayData,
            weatherWeekDayData,
            weatherWeekDayData,
            weatherWeekDayData,
            weatherWeekDayData,
            weatherWeekDayData,
        )

        WeekDisplay(weatherInfo) {}
    }
}