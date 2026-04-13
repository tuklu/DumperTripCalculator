package dev.tuklu.dumpertripcalculator.model

data class Vehicle(
    val id: Long = 0,
    val name: String,
    val capacityCubicMeters: Float,
    val costPerTrip: Float
)
