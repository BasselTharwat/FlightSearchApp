package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.domain.repository.FlightsearchRepository
import javax.inject.Inject

class SaveSearchQueryUseCase @Inject constructor(
    private val flightsearchRepository: FlightsearchRepository
) {
    suspend operator fun invoke(query: String) = flightsearchRepository.saveSearchQuery(query)
}