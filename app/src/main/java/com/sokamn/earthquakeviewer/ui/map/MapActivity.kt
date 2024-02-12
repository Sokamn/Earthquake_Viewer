package com.sokamn.earthquakeviewer.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context, latitude: Double, longitude: Double): Intent = Intent(context, MapActivity::class.java)
            .putExtra("latitude", latitude)
            .putExtra("longitude", longitude)
    }

    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMapBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        setUIComponents()
    }

    private fun setUIComponents() {
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        val annotationApi = binding.mpvEarthquakeMap.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager(binding.mpvEarthquakeMap)
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(longitude, latitude))
            .withIconImage(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(resources,
                R.drawable.placeholder
            ), 80, 80, false))

        pointAnnotationManager.create(pointAnnotationOptions)
        binding.mpvEarthquakeMap.getMapboxMap().also {
            it.setCamera(
                CameraOptions.Builder()
                    .center(Point.fromLngLat(longitude, latitude))
                    .zoom(6.0)
                    .build()
            )
        }
    }
}