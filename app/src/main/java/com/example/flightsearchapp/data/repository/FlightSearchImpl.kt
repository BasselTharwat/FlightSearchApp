package com.example.flightsearchapp.data.repository

import com.example.flightsearchapp.data.datastore.UserPreferencesManager
import com.example.flightsearchapp.data.local.FlightSearchDAO
import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlightSearchImpl @Inject constructor(
    private val flightSearchDAO: FlightSearchDAO,
    private val userPreferencesManager: UserPreferencesManager
) : FlightSearchRepository {
    override fun getSuggestions(query: String): Flow<List<AirportEntity>> = flightSearchDAO.getSuggestions(query)

    override fun getDestinationsFrom(departureCode: String): Flow<List<AirportEntity>> = flightSearchDAO.getDestinationsFrom(departureCode)

    override fun getAllFavorites(): Flow<List<FavoriteEntity>> = flightSearchDAO.getAllFavorites()

    override suspend fun insertFavorite(favorite: FavoriteEntity) = flightSearchDAO.insertFavorite(favorite)

    override fun getSearchHistory(): Flow<List<String>> = userPreferencesManager.searchHistory

    override suspend fun saveSearchHistory(query: String) = userPreferencesManager.saveSearchHistory(query)


}