package com.sokamn.earthquakeviewer

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_DAY
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_MOUNTH
import com.sokamn.earthquakeviewer.EarthquakeRepository.Companion.PER_WEEK

class ListFragment : Fragment() {
    private lateinit var rcvEarthquake: RecyclerView
    private lateinit var btnMenu: ImageButton
    private lateinit var btnDay: Button
    private lateinit var btnWeek: Button
    private lateinit var btnMounth: Button
    private val animationIn = AnimationSet(false)
    private val animationOut = AnimationSet(false)
    lateinit var adapter: EarthquakeAdapter

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
        val myView = inflater.inflate(R.layout.fragment_list, container, false)
        rcvEarthquake = myView.findViewById(R.id.rcvEarthquakes)
        btnMenu = myView.findViewById(R.id.btnMenu)
        btnMenu.tag = "closed"
        btnDay = myView.findViewById(R.id.btnDay)
        btnWeek = myView.findViewById(R.id.btnWeek)
        btnMounth = myView.findViewById(R.id.btnMounth)

        btnMenu.setOnClickListener {
            ActionMenu()
        }
        return myView
    }

    private fun ActionMenu() {
        if (btnMenu.tag=="closed"){
            OpenMenu()
        }else{
            CloseMenu()
        }
    }

    private fun CloseMenu() {
        btnMenu.setBackgroundResource(R.drawable.bg_button_unselected)
        btnMenu.setImageResource(R.drawable.menu_blue)
        btnDay.startAnimation(animationOut)
        btnWeek.startAnimation(animationOut)
        btnMounth.startAnimation(animationOut)
        btnDay.visibility = View.GONE
        btnWeek.visibility = View.GONE
        btnMounth.visibility = View.GONE
        btnMenu.tag = "closed"
    }

    private fun OpenMenu() {
        btnMenu.setBackgroundResource(R.drawable.bg_button_selected_menu)
        btnMenu.setImageResource(R.drawable.menu_white)
        btnDay.startAnimation(animationIn)
        btnWeek.startAnimation(animationIn)
        btnMounth.startAnimation(animationIn)
        btnDay.visibility = View.VISIBLE
        btnWeek.visibility = View.VISIBLE
        btnMounth.visibility = View.VISIBLE
        btnMenu.tag = "opened"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[EarthquakeViewModel::class.java]
        rcvEarthquake.layoutManager = LinearLayoutManager(requireActivity())
        adapter = EarthquakeAdapter()
        rcvEarthquake.adapter = adapter

        viewModel.list.observe(viewLifecycleOwner, Observer{ listEarthquake: MutableList<Earthquake> ->
            adapter.submitList(listEarthquake)
            handleEmptyList(listEarthquake, view)
        })

        adapter.onItemClickListener = {
            Toast.makeText(requireContext(), it.place, Toast.LENGTH_SHORT).show()
        }
        btnDay.setOnClickListener {
            if(btnDay.tag == "selected"){
                Toast.makeText(requireContext(),"Ya se muestran los terremotos del último día.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getXEarthquake(PER_DAY)
                setBtnDaySelected()
            }
        }
        btnWeek.setOnClickListener {
            if(btnWeek.tag == "selected"){
                Toast.makeText(requireContext(),"Ya se muestran los terremotos de la última semana.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getXEarthquake(PER_WEEK)
                setBtnWeekSelected()
            }
        }
        btnMounth.setOnClickListener {
            if(btnMounth.tag == "selected"){
                Toast.makeText(requireContext(),"Ya se muestran los terremotos del último mes.",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getXEarthquake(PER_MOUNTH)
                setBtnMounthSelected()
            }
        }
    }

    private fun setBtnMounthSelected() {
        btnDay.setBackgroundResource(R.drawable.bg_button_unselected)
        btnWeek.setBackgroundResource(R.drawable.bg_button_unselected)
        btnMounth.setBackgroundResource(R.drawable.bg_button_selected)
        btnDay.tag = "unselected"
        btnWeek.tag = "unselected"
        btnMounth.tag = "selected"
    }
    private fun setBtnWeekSelected() {
        btnDay.setBackgroundResource(R.drawable.bg_button_unselected)
        btnWeek.setBackgroundResource(R.drawable.bg_button_selected)
        btnMounth.setBackgroundResource(R.drawable.bg_button_unselected)
        btnDay.tag = "unselected"
        btnWeek.tag = "selected"
        btnMounth.tag = "unselected"
    }
    private fun setBtnDaySelected() {
        btnDay.setBackgroundResource(R.drawable.bg_button_selected)
        btnWeek.setBackgroundResource(R.drawable.bg_button_unselected)
        btnMounth.setBackgroundResource(R.drawable.bg_button_unselected)
        btnDay.tag = "selected"
        btnWeek.tag = "unselected"
        btnMounth.tag = "unselected"
    }

    private fun handleEmptyList(earthquakeList: MutableList<Earthquake>, view: View) {
        if (earthquakeList.isEmpty()){
            Snackbar.make(view,"No hay terremotos en esta franja de tiempo", Snackbar.LENGTH_SHORT).show()
        }
    }
}