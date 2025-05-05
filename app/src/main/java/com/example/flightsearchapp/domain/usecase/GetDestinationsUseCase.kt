package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDestinationsUseCase @Inject constructor(
    private val flightSearchRepository: FlightSearchRepository
) {
    operator fun invoke(departureCode: String): Flow<List<FavoriteEntity>> =
        flightSearchRepository.getDestinationsFrom(departureCode)
            .map {
                it.map { airportEntity ->
                    FavoriteEntity(
                        departureCode = departureCode,
                        destinationCode = airportEntity.iataCode
                    )
                }
            }
}