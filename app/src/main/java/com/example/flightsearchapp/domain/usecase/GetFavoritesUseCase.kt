package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import com.example.flightsearchapp.domain.repository.FlightsearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val flightsearchRepository: FlightsearchRepository
) {
    suspend operator fun invoke(): Flow<List<FavoriteEntity>?> = flightsearchRepository.getAllFavorites()
}