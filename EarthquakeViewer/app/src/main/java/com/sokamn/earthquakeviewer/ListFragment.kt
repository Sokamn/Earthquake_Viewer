package com.sokamn.earthquakeviewer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_DAY
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_MOUNTH
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_WEEK
import com.sokamn.earthquakeviewer.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val animationIn = AnimationSet(false)
    private val animationOut = AnimationSet(false)
    lateinit var adapter: EarthquakeAdapter
    private lateinit var listener: EarthquakeSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fadeIn: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        animationIn.addAnimation(fadeIn)
        animationOut.addAnimation(fadeOut)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.btnMenu.tag = "closed"

        binding.btnMenu.setOnClickListener {
            ActionMenu()
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as EarthquakeSelectedListener
    }

    private fun ActionMenu() {
        if (binding.btnMenu.tag=="closed"){
            OpenMenu()
        }else{
            CloseMenu()
        }
    }

    private fun CloseMenu() {
        binding.btnMenu.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnMenu.setImageResource(R.drawable.menu_blue)
        binding.btnDay.startAnimation(animationOut)
        binding.btnWeek.startAnimation(animationOut)
        binding.btnMounth.startAnimation(animationOut)
        binding.btnDay.visibility = View.GONE
        binding.btnWeek.visibility = View.GONE
        binding.btnMounth.visibility = View.GONE
        binding.btnMenu.tag = "closed"
    }

    private fun OpenMenu() {
        binding.btnMenu.setBackgroundResource(R.drawable.bg_button_selected_menu)
        binding.btnMenu.setImageResource(R.drawable.menu_white)
        binding.btnDay.startAnimation(animationIn)
        binding.btnWeek.startAnimation(animationIn)
        binding.btnMounth.startAnimation(animationIn)
        binding.btnDay.visibility = View.VISIBLE
        binding.btnWeek.visibility = View.VISIBLE
        binding.btnMounth.visibility = View.VISIBLE
        binding.btnMenu.tag = "opened"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, EarthquakeViewModelFactory(requireActivity().application))[EarthquakeViewModel::class.java]
        binding.rcvEarthquakes.layoutManager = LinearLayoutManager(requireActivity())
        adapter = EarthquakeAdapter()
        binding.rcvEarthquakes.adapter = adapter

        viewModel.list.observe(viewLifecycleOwner, Observer{ listEarthquake: MutableList<Earthquake> ->
            adapter.submitList(listEarthquake)
            handleEmptyList(listEarthquake, view)
        })

        viewModel.status.observe(viewLifecycleOwner, Observer{
            when(it){
                ApiResponseStatus.LOADING-> binding.psbProgressAPI.visibility = View.VISIBLE
                ApiResponseStatus.SUCCESS-> binding.psbProgressAPI.visibility = View.GONE
                ApiResponseStatus.ERROR-> binding.psbProgressAPI.visibility = View.GONE
            }
        })

        adapter.onItemClickListener = {
            listener.onEarthquakeSelected(it)
        }
        binding.btnDay.setOnClickListener {
            if(binding.btnDay.tag == "selected"){
                Toast.makeText(requireContext(),"Ya se muestran los terremotos del último día.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getXEarthquake(PER_DAY)
                setBtnDaySelected()
            }
        }
        binding.btnWeek.setOnClickListener {
            if(binding.btnWeek.tag == "selected"){
                Toast.makeText(requireContext(),"Ya se muestran los terremotos de la última semana.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getXEarthquake(PER_WEEK)
                setBtnWeekSelected()
            }
        }
        binding.btnMounth.setOnClickListener {
            if(binding.btnMounth.tag == "selected"){
                Toast.makeText(requireContext(),"Ya se muestran los terremotos del último mes.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getXEarthquake(PER_MOUNTH)
                setBtnMounthSelected()
            }
        }
    }

    private fun setBtnMounthSelected() {
        binding.btnDay.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnWeek.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnMounth.setBackgroundResource(R.drawable.bg_button_selected)
        binding.btnDay.tag = "unselected"
        binding.btnWeek.tag = "unselected"
        binding.btnMounth.tag = "selected"
    }
    private fun setBtnWeekSelected() {
        binding.btnDay.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnWeek.setBackgroundResource(R.drawable.bg_button_selected)
        binding.btnMounth.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnDay.tag = "unselected"
        binding.btnWeek.tag = "selected"
        binding.btnMounth.tag = "unselected"
    }
    private fun setBtnDaySelected() {
        binding.btnDay.setBackgroundResource(R.drawable.bg_button_selected)
        binding.btnWeek.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnMounth.setBackgroundResource(R.drawable.bg_button_unselected)
        binding.btnDay.tag = "selected"
        binding.btnWeek.tag = "unselected"
        binding.btnMounth.tag = "unselected"
    }

    private fun handleEmptyList(earthquakeList: MutableList<Earthquake>, view: View) {
        if (earthquakeList.isEmpty()){
            Snackbar.make(view,"No hay terremotos en esta franja de tiempo", Snackbar.LENGTH_SHORT).show()
        }
    }
}