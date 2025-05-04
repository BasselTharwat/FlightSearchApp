package com.example.flightsearchapp.domain.repository

import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FlightsearchRepository {
    suspend fun getSuggestions(query: String): Flow<List<AirportEntity>?>
    suspend fun getDestinationsFrom(departureCode: String): Flow<List<AirportEntity>?>
    suspend fun getAllFavorites(): Flow<List<FavoriteEntity>?>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    fun getSearchQuery(): Flow<String>
    suspend fun saveSearchQuery(query: String)

}