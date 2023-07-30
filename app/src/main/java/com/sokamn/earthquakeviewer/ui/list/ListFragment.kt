package com.sokamn.earthquakeviewer.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokamn.earthquakeviewer.R
import com.sokamn.earthquakeviewer.core.ex.hide
import com.sokamn.earthquakeviewer.core.ex.show
import com.sokamn.earthquakeviewer.core.ex.toast
import com.sokamn.earthquakeviewer.databinding.FragmentListBinding
import com.sokamn.earthquakeviewer.domain.model.Earthquake
import com.sokamn.earthquakeviewer.domain.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    // EarthquakeAdapter
    private val earthquakeAdapter =  EarthquakeAdapter()

    // Listeners

    // ViewModel
    private val listViewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

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
        listViewModel.earthquakeState.observe(viewLifecycleOwner){ result ->
            when(result){
                is Resource.Error -> handleError(result.message.toString())
                is Resource.Success -> handleSuccess(result)
                is Resource.Loading -> binding.psbProgressAPI.show()
                else-> binding.psbProgressAPI.hide()
            }
        }
    }

    private fun handleSuccess(result: Resource<List<Earthquake>>) {
        result.data?.let {
            earthquakeAdapter.submitList(result.data)
        }
        binding.psbProgressAPI.hide()
    }

    private fun handleError(errorMessage: String) {
        requireActivity().toast(errorMessage, Toast.LENGTH_SHORT)
        binding.psbProgressAPI.hide()
    }

    private fun initListeners() {

    }

    private fun setUIComponents() {
        binding.rcvEarthquakes.apply {
            adapter = earthquakeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            itemAnimator = DefaultItemAnimator()
        }
    }

}