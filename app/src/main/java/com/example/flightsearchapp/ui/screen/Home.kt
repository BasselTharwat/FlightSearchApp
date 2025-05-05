package com.example.flightsearchapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsearchapp.ui.component.DestinationList
import com.example.flightsearchapp.ui.component.FavoritesList
import com.example.flightsearchapp.ui.component.SearchHistoryList
import com.example.flightsearchapp.ui.component.SuggestionList
import com.example.flightsearchapp.ui.viewmodel.HomeUiState
import com.example.flightsearchapp.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery = viewModel.searchQuery
    val isSearchFocused = viewModel.isSearchFocused
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            onFocusChange = viewModel::onSearchFocusChanged
        )

        when {
            searchQuery.isNotBlank() -> {
                if (uiState is HomeUiState.Success) {
                    SuggestionList(
                        suggestions = (uiState as HomeUiState.Success).suggestions,
                        onSuggestionClick = { airport ->
                            coroutineScope.launch {
                                viewModel.saveSearchHistory(airport.name)
                                viewModel.getDestinations(airport.iataCode)
                            }
                        }
                    )
                }
            }
            isSearchFocused -> {
                if (uiState is HomeUiState.Success) {
                    SearchHistoryList(
                        history = (uiState as HomeUiState.Success).searchHistory
                    )
                }
            }
            else -> {
                if (uiState is HomeUiState.Success) {
                    FavoritesList(
                        favorites = (uiState as HomeUiState.Success).favorites,
                        onFavoriteClick = { favorite -> viewModel.saveFavorite(favorite) }
                    )
                }
            }
        }

        if (uiState is HomeUiState.Success && (uiState as HomeUiState.Success).destinations.isNotEmpty()) {
            DestinationList(destinations = (uiState as HomeUiState.Success).destinations)
        }
    }


}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                onFocusChange(it.isFocused)
            },
        placeholder = { Text("Search airports...") },
        trailingIcon = {
            if (query.isNotBlank()) {
                IconButton(onClick = {
                    onQueryChange("")
                    focusManager.clearFocus() // Remove focus
                    onFocusChange(false)      // Notify ViewModel
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        singleLine = true
    )
}
