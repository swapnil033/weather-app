package com.swapnil.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.swapnil.weatherapp.config.routes.AppNavigation
import com.swapnil.weatherapp.presentation.ui.theme.WeatherAppTheme
import com.swapnil.weatherapp.presentation.weather_page.viewModels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    //private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        /*permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )*/


        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {

                val navController = rememberNavController()

                AppNavigation(navController = navController)
            }
        }
    }
}