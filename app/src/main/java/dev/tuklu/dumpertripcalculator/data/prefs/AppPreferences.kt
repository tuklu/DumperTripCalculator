package dev.tuklu.dumpertripcalculator.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppPreferences(private val context: Context) {

    private object Keys {
        val contractorName = stringPreferencesKey("contractor_name")
        val contractorPhone = stringPreferencesKey("contractor_phone")
        val defaultVehicleProfileId = longPreferencesKey("default_vehicle_profile_id")
        val currencySymbol = stringPreferencesKey("currency_symbol")
        val currencyCode = stringPreferencesKey("currency_code")
        val isOnboardingComplete = booleanPreferencesKey("is_onboarding_complete")
        val defaultCostPerTrip = floatPreferencesKey("default_cost_per_trip")
        val defaultCapacityCubicMeters = floatPreferencesKey("default_capacity_cubic_meters")
    }

    val contractorName: Flow<String> = context.dataStore.data.map { it[Keys.contractorName] ?: "" }
    val contractorPhone: Flow<String> = context.dataStore.data.map { it[Keys.contractorPhone] ?: "" }
    val defaultVehicleProfileId: Flow<Long> = context.dataStore.data.map { it[Keys.defaultVehicleProfileId] ?: -1L }
    val currencySymbol: Flow<String> = context.dataStore.data.map { it[Keys.currencySymbol] ?: "₹" }
    val currencyCode: Flow<String> = context.dataStore.data.map { it[Keys.currencyCode] ?: "INR" }
    val isOnboardingComplete: Flow<Boolean> = context.dataStore.data.map { it[Keys.isOnboardingComplete] ?: false }
    val defaultCostPerTrip: Flow<Float> = context.dataStore.data.map { it[Keys.defaultCostPerTrip] ?: 0f }
    val defaultCapacityCubicMeters: Flow<Float> = context.dataStore.data.map { it[Keys.defaultCapacityCubicMeters] ?: 0f }

    suspend fun setContractorName(value: String) { context.dataStore.edit { it[Keys.contractorName] = value } }
    suspend fun setContractorPhone(value: String) { context.dataStore.edit { it[Keys.contractorPhone] = value } }
    suspend fun setDefaultVehicleProfileId(value: Long) { context.dataStore.edit { it[Keys.defaultVehicleProfileId] = value } }
    suspend fun setCurrencySymbol(value: String) { context.dataStore.edit { it[Keys.currencySymbol] = value } }
    suspend fun setCurrencyCode(value: String) { context.dataStore.edit { it[Keys.currencyCode] = value } }
    suspend fun setOnboardingComplete(value: Boolean) { context.dataStore.edit { it[Keys.isOnboardingComplete] = value } }
    suspend fun setDefaultCostPerTrip(value: Float) { context.dataStore.edit { it[Keys.defaultCostPerTrip] = value } }
    suspend fun setDefaultCapacityCubicMeters(value: Float) { context.dataStore.edit { it[Keys.defaultCapacityCubicMeters] = value } }
}
