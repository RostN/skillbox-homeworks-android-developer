package com.example.hw24.ui

import android.app.Application
import androidx.room.*
import kotlinx.coroutines.flow.Flow

class App : Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}

@Database(entities = [Cities::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CitiesDao(): CitiesDao
}

@Entity(tableName = "Cities")
data class Cities(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null, //ИД
    @ColumnInfo(name = "CityName") var name: String, //Название
    @ColumnInfo(name = "Latitude") var lat: String, // Широта
    @ColumnInfo(name = "Longitude") var lng: String, // Долгота
    @ColumnInfo(name = "Dates") var dates: String? = null, // Даты
    @ColumnInfo(name = "Temperatures") var temps: String? = null, // Температуры
    @ColumnInfo(name = "Last updates") var lastUpdates: String? = null // Последнее обновление
)

@Dao
interface CitiesDao {
    @Query("SELECT*FROM Cities")
    fun getAll(): Flow<List<Cities>>

    @Insert
    suspend fun insert(data: Cities)

    @Delete
    suspend fun delete(data: List<Cities>)

    @Update
    suspend fun update(data: Cities)

    @Query("SELECT*FROM Cities WHERE CityName LIKE :enterText")
    suspend fun searchWords(enterText: String): List<Cities>

    @Query("SELECT*FROM Cities WHERE CityName LIKE '%' || :enterText || '%'")
    suspend fun searchWordsPart(enterText: String): List<Cities>
}