package dev.tuklu.dumpertripcalculator.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
val CURRENCY_CODE = stringPreferencesKey("currency_code")
val IS_ONBOARDING_COMPLETE = booleanPreferencesKey("is_onboarding_complete")

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

fun readIsOnboardingComplete(context: Context): Flow<Boolean> =
    context.dataStore.data.map { prefs -> prefs[IS_ONBOARDING_COMPLETE] ?: false }

suspend fun saveOnboardingPrefs(
    context: Context,
    currencySymbol: String,
    currencyCode: String
) {
    context.dataStore.updateData { prefs ->
        prefs.toMutablePreferences().apply {
            set(CURRENCY_SYMBOL, currencySymbol)
            set(CURRENCY_CODE, currencyCode)
            set(IS_ONBOARDING_COMPLETE, true)
        }
    }
}
