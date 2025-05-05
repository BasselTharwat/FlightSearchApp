package com.example.flightsearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearchapp.ui.FlightSearchApp
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightSearchAppTheme {
                Scaffold {
                    FlightSearchApp(
                        modifier = Modifier.padding(it),
                        viewModel = hiltViewModel()
                    )
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlightSearchAppTheme {
    }
}