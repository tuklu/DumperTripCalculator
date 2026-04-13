package dev.tuklu.dumpertripcalculator.data.repository

import dev.tuklu.dumpertripcalculator.data.db.VehicleDao
import dev.tuklu.dumpertripcalculator.data.db.toDomain
import dev.tuklu.dumpertripcalculator.data.db.toEntity
import dev.tuklu.dumpertripcalculator.model.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VehicleRepository(private val dao: VehicleDao) {
    fun getAll(): Flow<List<Vehicle>> = dao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun getById(id: Long): Vehicle? = dao.getById(id)?.toDomain()

    suspend fun insert(vehicle: Vehicle): Long = dao.insert(vehicle.toEntity())

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun update(vehicle: Vehicle) = dao.update(vehicle.toEntity())
}
