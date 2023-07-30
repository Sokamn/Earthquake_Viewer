package com.sokamn.earthquakeviewer.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "earthquake")
data class Earthquake (
    @PrimaryKey val id: String,
    val place: String,
    val magType: String,
    val magnitude: Double,
    val duracion: Long,
    val latitude: Double,
    val longitude: Double,
    val tsunami: Int,
    val url: String
): Parcelable