package com.sokamn.earthquakeviewer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EarthquakeDao {
    @Insert
    fun insertAll (earthquakeList: MutableList<Earthquake>)
    @Query("SELECT * FROM earthquake")
    fun getAll(): MutableList<Earthquake>
    @Query("SELECT * FROM earthquake WHERE magnitude > :mag")
    fun getAllByMagnitude(mag: Double): MutableList<Earthquake>
    @Update
    fun updateEartquake(earthquake: Earthquake)
    @Delete
    fun deleteEarthquakes(vararg earthquake: Earthquake)
}