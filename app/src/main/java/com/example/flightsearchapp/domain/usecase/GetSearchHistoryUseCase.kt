package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val flightSearchRepository: FlightSearchRepository
) {
    operator fun invoke(): Flow<List<String>> = flightSearchRepository.getSearchHistory()

}