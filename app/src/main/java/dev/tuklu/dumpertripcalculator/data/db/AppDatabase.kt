package dev.tuklu.dumpertripcalculator.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [QuoteEntity::class, ClientEntity::class, VehicleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MaterialTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun clientDao(): ClientDao
    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dumper_trip_calculator.db"
                ).build().also { instance = it }
            }
    }
}
