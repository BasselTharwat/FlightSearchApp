package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSuggestionsUseCase @Inject constructor(
    private val flightSearchRepository: FlightSearchRepository
) {
    operator fun invoke(query: String): Flow<List<AirportEntity>> = flightSearchRepository.getSuggestions(query)
}