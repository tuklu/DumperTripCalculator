package dev.tuklu.dumpertripcalculator.data.repository

import dev.tuklu.dumpertripcalculator.data.db.ClientDao
import dev.tuklu.dumpertripcalculator.data.db.toDomain
import dev.tuklu.dumpertripcalculator.data.db.toEntity
import dev.tuklu.dumpertripcalculator.model.Client
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientRepository(private val dao: ClientDao) {
    fun getAll(): Flow<List<Client>> = dao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun getById(id: Long): Client? = dao.getById(id)?.toDomain()

    suspend fun insert(client: Client): Long = dao.insert(client.toEntity())

    suspend fun deleteById(id: Long) = dao.deleteById(id)
}
