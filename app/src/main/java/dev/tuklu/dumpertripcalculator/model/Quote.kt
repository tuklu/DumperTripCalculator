package dev.tuklu.dumpertripcalculator.model

data class Quote(
    val id: Long = 0,
    val jobName: String,
    val materialType: MaterialType,
    val volumeCubicMeters: Float,
    val trips: Int,
    val costPerTrip: Float,
    val totalCost: Float,
    val vehicleProfileId: Long?,
    val clientId: Long?,
    val createdAt: Long,
    val notes: String = ""
)
