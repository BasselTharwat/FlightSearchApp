package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val flightSearchRepository: FlightSearchRepository
) {
    suspend operator fun invoke(favorite: FavoriteEntity) = flightSearchRepository.insertFavorite(favorite)
}