package com.example.connectify.domain.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.connectify.utils.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ThemeRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    private val THEME_KEY = intPreferencesKey("theme_mode")

    val themeModeFlow: Flow<ThemeMode> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { preferences ->
            val saved = preferences[THEME_KEY]
            when (saved) {
                1 -> ThemeMode.LIGHT
                2 -> ThemeMode.DARK
                else -> ThemeMode.FOLLOW_SYSTEM
            }
        }
        .distinctUntilChanged()

    suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            when (mode) {
                ThemeMode.LIGHT -> preferences[THEME_KEY] = 1
                ThemeMode.DARK -> preferences[THEME_KEY] = 2
                ThemeMode.FOLLOW_SYSTEM -> preferences.remove(THEME_KEY)
            }
        }
    }

}