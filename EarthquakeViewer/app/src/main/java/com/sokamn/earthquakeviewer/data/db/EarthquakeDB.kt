package com.sokamn.earthquakeviewer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sokamn.earthquakeviewer.model.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EarthquakeDB: RoomDatabase() {
    abstract val earthquakeDao: EarthquakeDao
}

private lateinit var INSTANCE: EarthquakeDB

fun getDatabase(context: Context) : EarthquakeDB {
    synchronized(EarthquakeDB::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EarthquakeDB::class.java,
                "earthquake_db"
            ).build()
        }
        return INSTANCE
    }
}