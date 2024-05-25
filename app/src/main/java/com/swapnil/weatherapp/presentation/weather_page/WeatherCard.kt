package com.swapnil.weatherapp.presentation.weather_page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swapnil.weatherapp.R
import com.swapnil.weatherapp.domain.weather.WeatherData
import com.swapnil.weatherapp.domain.weather.WeatherInfo
import com.swapnil.weatherapp.domain.weather.WeatherType
import com.swapnil.weatherapp.presentation.ui.theme.WeatherAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {

    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Today ${
                        data.time.format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                    }", modifier = Modifier.align(alignment = Alignment.End), color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}°C",
                    fontSize = 40.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 30.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WeatherCardFooter(
                        value = data.pressure.roundToInt(),
                        unit = "hpa",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
                    )
                    WeatherCardFooter(
                        value = data.humidity.roundToInt(),
                        unit = "%",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                    )
                    WeatherCardFooter(
                        value = data.windSpeed.roundToInt(),
                        unit = "km/h",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                    )
                }
            }
        }
    }

}


@Preview
@Composable
private fun WeatherCardPreview() {
    WeatherAppTheme {
        val state = WeatherState(
            weatherInfo = WeatherInfo(
                weatherDataPerDay = mapOf(), currentWeatherData = WeatherData(
                    time = LocalDateTime.now(),
                    temperatureCelsius = 0.0,
                    pressure = 0.0,
                    humidity = 0.0,
                    windSpeed = 0.0,
                    weatherType = WeatherType.fromWMO(0)
                )
            )
        )
        val color = Color.Gray
        WeatherCard(
            state = state, backgroundColor = color
        )
    }
}