package dev.tuklu.dumpertripcalculator.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dev.tuklu.dumpertripcalculator.model.MaterialType
import dev.tuklu.dumpertripcalculator.model.Quote

class MaterialTypeConverter {
    @TypeConverter
    fun fromMaterialType(value: MaterialType): String = value.name

    @TypeConverter
    fun toMaterialType(value: String): MaterialType = MaterialType.valueOf(value)
}

@Entity(tableName = "quotes")
@TypeConverters(MaterialTypeConverter::class)
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobName: String,
    val materialType: MaterialType,
    val volumeCubicMeters: Float,
    val trips: Int,
    val costPerTrip: Float,
    val totalCost: Float,
    val vehicleProfileId: Long?,
    val clientId: Long?,
    val createdAt: Long,
    val notes: String
)

fun QuoteEntity.toDomain() = Quote(
    id = id,
    jobName = jobName,
    materialType = materialType,
    volumeCubicMeters = volumeCubicMeters,
    trips = trips,
    costPerTrip = costPerTrip,
    totalCost = totalCost,
    vehicleProfileId = vehicleProfileId,
    clientId = clientId,
    createdAt = createdAt,
    notes = notes
)

fun Quote.toEntity() = QuoteEntity(
    id = id,
    jobName = jobName,
    materialType = materialType,
    volumeCubicMeters = volumeCubicMeters,
    trips = trips,
    costPerTrip = costPerTrip,
    totalCost = totalCost,
    vehicleProfileId = vehicleProfileId,
    clientId = clientId,
    createdAt = createdAt,
    notes = notes
)
