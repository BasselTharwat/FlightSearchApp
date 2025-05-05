package com.example.flightsearchapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.data.local.entity.AirportEntity

@Composable
fun FavoritesList(
    favorites: List<FavoriteEntity>,
    onFavoriteClick: (FavoriteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(favorites) { favorite ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("From: ${favorite.departureCode} To: ${favorite.destinationCode}")
                IconButton(onClick = { onFavoriteClick(favorite) }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Add to Favorites"
                    )
                }
            }
        }
    }
}


@Composable
fun SearchHistoryList(history: List<String>,
                      modifier: Modifier = Modifier) {
    LazyColumn {
        items(history) { item ->
            Text(text = item)
        }
    }
}

@Composable
fun SuggestionList(suggestions: List<AirportEntity>,
                   onSuggestionClick: (AirportEntity) -> Unit,
                   modifier: Modifier = Modifier) {
    LazyColumn {
        items(suggestions) { airport ->
            Text(
                text = "${airport.name} (${airport.iataCode})",
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(airport) }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DestinationList(destinations: List<FavoriteEntity>) {
    LazyColumn {
        items(destinations) { destination ->
            Text("To: ${destination.destinationCode}")
        }
    }
}
