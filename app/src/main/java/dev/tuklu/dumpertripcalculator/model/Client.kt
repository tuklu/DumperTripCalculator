package dev.tuklu.dumpertripcalculator.model

data class Client(
    val id: Long = 0,
    val name: String,
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val createdAt: Long
)
