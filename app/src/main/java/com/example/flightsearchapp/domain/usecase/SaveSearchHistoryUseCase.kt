package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import javax.inject.Inject

class SaveSearchHistoryUseCase @Inject constructor(
    private val flightSearchRepository: FlightSearchRepository
) {
    suspend operator fun invoke(query: String) = flightSearchRepository.saveSearchHistory(query)
}