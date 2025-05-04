package com.example.flightsearchapp.data.repository

import com.example.flightsearchapp.data.datastore.UserPreferencesManager
import com.example.flightsearchapp.data.local.FlightsearchDAO
import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.repository.FlightsearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlightsearchImpl @Inject constructor(
    private val flightsearchDAO: FlightsearchDAO,
    private val userPreferencesManager: UserPreferencesManager
) : FlightsearchRepository {
    override suspend fun getSuggestions(query: String): Flow<List<AirportEntity>?> = flightsearchDAO.getSuggestions(query)

    override suspend fun getDestinationsFrom(departureCode: String): Flow<List<AirportEntity>?> = flightsearchDAO.getDestinationsFrom(departureCode)

    override suspend fun getAllFavorites(): Flow<List<FavoriteEntity>?> = flightsearchDAO.getAllFavorites()

    override suspend fun insertFavorite(favorite: FavoriteEntity) = flightsearchDAO.insertFavorite(favorite)

    override fun getSearchQuery(): Flow<String> = userPreferencesManager.searchQuery

    override suspend fun saveSearchQuery(query: String) = userPreferencesManager.saveSearchQuery(query)

}