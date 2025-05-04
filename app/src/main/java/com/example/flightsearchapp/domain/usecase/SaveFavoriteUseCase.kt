package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.repository.FlightsearchRepository
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val flightsearchRepository: FlightsearchRepository
) {
    suspend operator fun invoke(favorite: FavoriteEntity) = flightsearchRepository.insertFavorite(favorite)
}