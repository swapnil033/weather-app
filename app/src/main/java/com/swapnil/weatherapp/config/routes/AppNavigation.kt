package com.swapnil.weatherapp.config.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.swapnil.weatherapp.presentation.weather_page.WeatherScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = DashboardScreen) {
        composable<DashboardScreen> {
            WeatherScreenRoot(navController = navController)
        }
    }
}


@Serializable
object DashboardScreen