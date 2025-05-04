package com.example.flightsearchapp.data.datastore

import android.util.Log
import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferencesManager(
    private val dataStore: DataStore<Preferences>
) {


    companion object {
        val SEARCH_QUERY_KEY = stringPreferencesKey("search_query")
        const val TAG = "UserPreferencesManager"
    }

    val searchQuery: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs -> prefs[SEARCH_QUERY_KEY] ?: "" }

    suspend fun saveSearchQuery(query: String) {
        dataStore.edit { prefs ->
            prefs[SEARCH_QUERY_KEY] = query
        }
    }
}
