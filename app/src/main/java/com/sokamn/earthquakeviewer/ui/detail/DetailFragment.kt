package com.sokamn.earthquakeviewer.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.databinding.FragmentDetailBinding
import com.sokamn.earthquakeviewer.databinding.FragmentListBinding
import com.sokamn.earthquakeviewer.domain.model.Earthquake
import com.sokamn.earthquakeviewer.ui.map.MapActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        setUIComponents()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        detailViewModel.navigateToMapActivity.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                goToMapActivity()
            }
        }
    }

    private fun setUIComponents() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        setUpVibration(vibrator)
        renderEarthquake(args.earthquake)
    }

    private fun initListeners() {
        binding.imvPlaceHolderGPS.setOnClickListener {
            detailViewModel.onPlaceHolderGPSSelected()
        }

        binding.btnMoreInfoFD.setOnClickListener {
            val link = Uri.parse(args.earthquake.url)
            startActivity(Intent(Intent.ACTION_VIEW, link))
        }
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

    private fun setUpVibration(vibrator: Vibrator) {
        if(args.earthquake.magnitude<5.5){
            binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_low)
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        }else if(args.earthquake.magnitude<7){
            binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_medium)
            vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            binding.imvMagnitudeFD.setImageResource(R.drawable.speedometer_high)
            vibrator.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }

    private fun renderEarthquake(selectedEarthquake: Earthquake) {
        val date = convertTimeToDate(selectedEarthquake.duracion)
        val hour = convertTimeToHour(selectedEarthquake.duracion)

        with(binding){
            txvTitleEarthquake.text = selectedEarthquake.place
            txvDateEarthquake.text = date
            txvHourEarthquake.text = hour
            txvTsunamiEarthquake.text = selectedEarthquake.tsunami.toString()
            txvMagnitudeEarthquake.text = "%.2f".format(selectedEarthquake.magnitude)
            txvEarthquakeID.text = "ID: ${selectedEarthquake.id}"
            txvEarthquakeCords.text = "Lat: ${selectedEarthquake.latitude} Lon: ${selectedEarthquake.longitude}"
            txvKindMagnitudeFD.text = "Tipo de Magnitud: ${selectedEarthquake.magType.uppercase()}"
        }
    }

    private fun goToMapActivity() {
        startActivity(MapActivity.create(requireActivity(), args.earthquake.latitude, args.earthquake.longitude))
    }
}