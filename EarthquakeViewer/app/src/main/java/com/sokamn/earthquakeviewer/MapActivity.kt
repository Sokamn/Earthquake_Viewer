package com.sokamn.earthquakeviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.image
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.sokamn.earthquakeviewer.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val bundle = intent.extras
        val earthquake: Earthquake = bundle?.getParcelable("earthquakeSelected")!!

        val annotationApi = binding.mpvEarthquakeMap.annotations
        val pointAnnotationManager = annotationApi?.createPointAnnotationManager(binding.mpvEarthquakeMap)
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(earthquake.longitude, earthquake.latitude))
            .withIconImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.placeholder), 80, 80, false))
        pointAnnotationManager?.create(pointAnnotationOptions)
        binding.mpvEarthquakeMap.getMapboxMap().also {
            it.setCamera(
                CameraOptions.Builder()
                    .center(Point.fromLngLat(earthquake.longitude, earthquake.latitude))
                    .zoom(6.0)
                    .build()
            )
        }

    }
}