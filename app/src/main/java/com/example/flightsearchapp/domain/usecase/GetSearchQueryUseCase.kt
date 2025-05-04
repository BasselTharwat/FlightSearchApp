package com.example.flightsearchapp.domain.usecase

import com.example.flightsearchapp.domain.repository.FlightsearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchQueryUseCase @Inject constructor(
    private val flightsearchRepository: FlightsearchRepository
) {
    operator fun invoke(): Flow<String> = flightsearchRepository.getSearchQuery()

}