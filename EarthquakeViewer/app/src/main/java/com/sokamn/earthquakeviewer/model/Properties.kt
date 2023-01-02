package com.sokamn.earthquakeviewer.model

data class Properties (
        val mag: Double,
        val place: String,
        val time: Long,
        val url: String,
        val tsunami: Int,
        val magType: String
        )