package com.example.flightsearchapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.flightsearchapp.data.datastore.UserPreferencesManager
import com.example.flightsearchapp.data.local.FlightSearchDAO
import com.example.flightsearchapp.data.local.FlightSearchDB
import com.example.flightsearchapp.data.repository.FlightSearchImpl
import com.example.flightsearchapp.domain.repository.FlightSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val USER_PREFS = "user_prefs"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFS)


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideUserPreferencesManager(dataStore: DataStore<Preferences>): UserPreferencesManager =
        UserPreferencesManager(dataStore)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FlightSearchDB {
        return Room.databaseBuilder(
            context,
            FlightSearchDB::class.java,
            "flight_search.db"
        )
            .createFromAsset("flight_search.db") // 👈 Loads prebuilt DB from assets
            .build()
    }

    @Provides
    fun provideDao(db: FlightSearchDB): FlightSearchDAO = db.flightSearchDAO()

    @Provides
    @Singleton
    fun provideFlightsearchRepository(dao: FlightSearchDAO, userPreferencesManager: UserPreferencesManager): FlightSearchRepository =
        FlightSearchImpl(dao, userPreferencesManager)


}