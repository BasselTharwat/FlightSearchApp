package com.example.flightsearchapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearchapp.ui.screen.Home
import com.example.flightsearchapp.ui.viewmodel.HomeViewModel

@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Home(viewModel = viewModel,
        modifier = modifier)
}