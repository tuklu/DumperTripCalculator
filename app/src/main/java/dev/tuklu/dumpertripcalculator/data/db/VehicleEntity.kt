package dev.tuklu.dumpertripcalculator.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.tuklu.dumpertripcalculator.model.Vehicle

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val capacityCubicMeters: Float,
    val costPerTrip: Float
)

fun VehicleEntity.toDomain() = Vehicle(
    id = id,
    name = name,
    capacityCubicMeters = capacityCubicMeters,
    costPerTrip = costPerTrip
)

fun Vehicle.toEntity() = VehicleEntity(
    id = id,
    name = name,
    capacityCubicMeters = capacityCubicMeters,
    costPerTrip = costPerTrip
)
