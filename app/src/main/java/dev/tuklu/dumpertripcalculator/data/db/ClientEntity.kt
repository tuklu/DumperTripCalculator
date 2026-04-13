package dev.tuklu.dumpertripcalculator.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.tuklu.dumpertripcalculator.model.Client

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val createdAt: Long
)

fun ClientEntity.toDomain() = Client(
    id = id,
    name = name,
    phone = phone,
    email = email,
    address = address,
    createdAt = createdAt
)

fun Client.toEntity() = ClientEntity(
    id = id,
    name = name,
    phone = phone,
    email = email,
    address = address,
    createdAt = createdAt
)
