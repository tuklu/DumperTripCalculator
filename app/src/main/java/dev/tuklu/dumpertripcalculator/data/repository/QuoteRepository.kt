package dev.tuklu.dumpertripcalculator.data.repository

import dev.tuklu.dumpertripcalculator.data.db.QuoteDao
import dev.tuklu.dumpertripcalculator.data.db.toDomain
import dev.tuklu.dumpertripcalculator.data.db.toEntity
import dev.tuklu.dumpertripcalculator.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteRepository(private val dao: QuoteDao) {
    fun getAll(): Flow<List<Quote>> = dao.getAll().map { list -> list.map { it.toDomain() } }

    suspend fun getById(id: Long): Quote? = dao.getById(id)?.toDomain()

    suspend fun insert(quote: Quote): Long = dao.insert(quote.toEntity())

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun deleteOlderThan(cutoff: Long) = dao.deleteOlderThan(cutoff)
}
