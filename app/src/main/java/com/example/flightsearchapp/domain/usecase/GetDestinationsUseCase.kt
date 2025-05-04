package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.domain.repository.FlightsearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDestinationsUseCase @Inject constructor(
    private val flightsearchRepository: FlightsearchRepository
) {
    suspend operator fun invoke(departureCode: String): Flow<List<AirportEntity>?> =
        flightsearchRepository.getDestinationsFrom(departureCode)
}