package com.example.flightsearchapp.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPreferencesManager(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
        const val TAG = "UserPreferencesManager"
    }

    val searchHistory: Flow<List<String>> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs ->
            val json = prefs[SEARCH_HISTORY_KEY] ?: "[]"
            try {
                Json.decodeFromString<List<String>>(json)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to parse search history JSON", e)
                emptyList()
            }
        }

    suspend fun saveSearchHistory(query: String) {
        dataStore.edit { prefs ->
            val currentHistory = prefs[SEARCH_HISTORY_KEY]?.let {
                try {
                    Json.decodeFromString<List<String>>(it)
                } catch (e: Exception) {
                    emptyList()
                }
            } ?: emptyList()

            val newHistory = listOf(query) + currentHistory.filter { it != query }
            val limitedHistory = newHistory.take(3)
            prefs[SEARCH_HISTORY_KEY] = Json.encodeToString(limitedHistory)
        }
    }
}
