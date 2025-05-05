package com.example.flightsearchapp.domain.repository

import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FlightSearchRepository {
    fun getSuggestions(query: String): Flow<List<AirportEntity>>
    fun getDestinationsFrom(departureCode: String): Flow<List<AirportEntity>>
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    fun getSearchHistory(): Flow<List<String>>
    suspend fun saveSearchHistory(query: String)

}