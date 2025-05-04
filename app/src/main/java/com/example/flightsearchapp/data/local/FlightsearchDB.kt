package com.example.flightsearchapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flightsearchapp.data.local.entity.AirportEntity
import com.example.flightsearchapp.data.local.entity.FavoriteEntity

@Database(entities = [AirportEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class FlightsearchDB : RoomDatabase() {
    abstract fun flightsearchDAO() : FlightsearchDAO
}