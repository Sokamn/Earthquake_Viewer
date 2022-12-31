package com.sokamn.earthquakeviewer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sokamn.earthquakeviewer.databinding.FragmentDetailBinding
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*


class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedEarthquake = args.earthquake
        renderActivity(selectedEarthquake)

        binding.imvPlaceHolderGPS.setOnClickListener {
            val intent = Intent(activity, MapActivity::class.java)
            intent.putExtra("earthquakeSelected",selectedEarthquake)
            startActivity(intent)
        }
        binding.btnMoreInfoFD.setOnClickListener {
            val link = Uri.parse(selectedEarthquake.url)
            startActivity(Intent(Intent.ACTION_VIEW, link))
        }
    }

    private fun renderActivity(earthquake: Earthquake) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            if(earthquake.magnitude<5.5){
                binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_low)
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            }else if(earthquake.magnitude<7){
                binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_medium)
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_CLICK))
            }else{
                binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_high)
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_HEAVY_CLICK))
            }
        } else {
            if(earthquake.magnitude<5.5){
                binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_low)
                vibrator.vibrate(500)
            }else if(earthquake.magnitude<7){
                binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_medium)
                vibrator.vibrate(1000)
            }else{
                binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_high)
                vibrator.vibrate(1500)
            }
        }



        val date = convertTimeToDate(earthquake.duracion)
        val hour = convertTimeToHour(earthquake.duracion)

        binding.txvTitleEarthquake.text = earthquake.place
        binding.txvDateEarthquake.text = date
        binding.txvHourEarthquake.text = hour
        binding.txvTsunamiEarthquake.text = earthquake.tsunami.toString()
        binding.txvMagnitudeEarthquake.text = earthquake.magnitude.toString()
        binding.txvEarthquakeID.text = "ID: ${earthquake.id}"
        binding.txvEarthquakeCords.text = "Lat: ${earthquake.latitude} Lon: ${earthquake.longitude}"
        binding.txvKindMagnitudeFD.text = "Tipo de Magnitud: ${earthquake.magType.uppercase()}"
    }

    private fun convertTimeToHour(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }

    private fun convertTimeToDate(time: Long): String {
        val date = Date(time)
        val format: Format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }

}