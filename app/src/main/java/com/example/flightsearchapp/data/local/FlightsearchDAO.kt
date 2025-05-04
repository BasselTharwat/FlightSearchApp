package com.example.flightsearchapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightsearchDAO {

    @Query("""
    SELECT * FROM airport
    WHERE iata_code LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%'
    ORDER BY passengers DESC
""")
    suspend fun getSuggestions(query: String): Flow<List<AirportEntity>?>


    @Query("""
        SELECT * FROM airport
        WHERE iata_code != :departureCode
    """)
    suspend fun getDestinationsFrom(departureCode: String): Flow<List<AirportEntity>?>

    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorites(): Flow<List<FavoriteEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)
}
