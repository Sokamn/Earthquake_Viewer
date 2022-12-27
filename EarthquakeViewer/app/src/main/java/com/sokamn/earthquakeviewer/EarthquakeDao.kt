package com.sokamn.earthquakeviewer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EarthquakeDao {
    @Insert
    fun insertAll (earthquakeList: MutableList<Earthquake>)
    @Query("SELECT * FROM earthquake")
    fun getAll(): MutableList<Earthquake>
    @Query("SELECT * FROM earthquake WHERE magnitude > :mag")
    fun getAllByMagnitude(mag: Double): MutableList<Earthquake>
}