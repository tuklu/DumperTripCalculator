package dev.tuklu.dumpertripcalculator.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: QuoteEntity): Long

    @Query("SELECT * FROM quotes ORDER BY createdAt DESC")
    fun getAll(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getById(id: Long): QuoteEntity?

    @Query("DELETE FROM quotes WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM quotes WHERE createdAt < :cutoff")
    suspend fun deleteOlderThan(cutoff: Long)
}
