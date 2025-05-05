package com.example.flightsearchapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.usecase.GetDestinationsUseCase
import com.example.flightsearchapp.domain.usecase.GetFavoritesUseCase
import com.example.flightsearchapp.domain.usecase.GetSearchHistoryUseCase
import com.example.flightsearchapp.domain.usecase.GetSuggestionsUseCase
import com.example.flightsearchapp.domain.usecase.SaveFavoriteUseCase
import com.example.flightsearchapp.domain.usecase.SaveSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface HomeUiState{
    data class Success(val suggestions: List<AirportEntity> = emptyList(),
                       val destinations: List<FavoriteEntity> = emptyList(),
                       val favorites: List<FavoriteEntity> = emptyList(),
                       val searchHistory: List<String> = emptyList()): HomeUiState
    object Error: HomeUiState
    object LoadingFavorites: HomeUiState
    object LoadingSuggestions: HomeUiState
    object LoadingDestinations: HomeUiState
    object LoadingSearchHistory: HomeUiState
}


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val getDestinationsUseCase: GetDestinationsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val saveSearchHistoryUseCase: SaveSearchHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.LoadingFavorites)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    var isSearchFocused by mutableStateOf(false)
        private set

    init {
        getSearchHistory()
        getFavorites()

    }

    private fun getSearchHistory() {
        getSearchHistoryUseCase()
            .onStart {
                _uiState.value = HomeUiState.LoadingSearchHistory
            }
            .catch {
                _uiState.value = HomeUiState.Error
            }
            .onEach {
                _uiState.update { current ->
                    val success = current as? HomeUiState.Success ?: HomeUiState.Success()
                    success.copy(searchHistory = it)
                }

            }
            .launchIn(viewModelScope)
    }

    private fun getFavorites() {
        getFavoritesUseCase()
            .onStart { _uiState.value = HomeUiState.LoadingFavorites }
            .catch { _uiState.value = HomeUiState.Error }
            .onEach { favorites ->
                _uiState.update { current ->
                    val success = current as? HomeUiState.Success ?: HomeUiState.Success()
                    success.copy(favorites = favorites)
                }
            }
            .launchIn(viewModelScope)

    }

    private fun getSuggestions(query: String) {
        getSuggestionsUseCase(query)
            .onStart {
                _uiState.value = HomeUiState.LoadingSuggestions
            }
            .catch {
                _uiState.value = HomeUiState.Error
            }
            .onEach { suggestions ->
                _uiState.update { current ->
                    val success = current as? HomeUiState.Success ?: HomeUiState.Success()
                    success.copy(suggestions = suggestions)
                }
            }
            .launchIn(viewModelScope)

    }

    fun getDestinations(departureCode: String) {
        getDestinationsUseCase(departureCode)
            .onStart {
                _uiState.value = HomeUiState.LoadingDestinations
            }
            .catch {
                _uiState.value = HomeUiState.Error
            }
            .onEach { destinations ->
                _uiState.update { current ->
                    val success = current as? HomeUiState.Success ?: HomeUiState.Success()
                    success.copy(destinations = destinations)
                }
            }
            .launchIn(viewModelScope)
    }

    fun saveFavorite(favorite: FavoriteEntity){
        viewModelScope.launch {
            saveFavoriteUseCase(favorite)
        }
    }

    fun saveSearchHistory(query: String){
        viewModelScope.launch {
            saveSearchHistoryUseCase(query)
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
        if (newQuery.isNotBlank()) {
            viewModelScope.launch {
                getSuggestions(newQuery)
            }
        }
    }

    fun onSearchFocusChanged(focused: Boolean) {
        isSearchFocused = focused
    }

}