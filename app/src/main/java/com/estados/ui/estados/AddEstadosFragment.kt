package com.estados.ui.estados

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.estados.R
import com.estados.databinding.FragmentAddEstadosBinding
import com.estados.model.Estados
import com.estados.viewmodel.EstadosViewModel

class AddEstadosFragment : Fragment() {
    private var _binding: FragmentAddEstadosBinding? = null
    private val binding get() = _binding!!
    private lateinit var estadosViewModel: EstadosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        estadosViewModel =  ViewModelProvider(this).get(EstadosViewModel::class.java)
        _binding = FragmentAddEstadosBinding.inflate(inflater, container, false)

        binding.btAddEstados.setOnClickListener { addEstados() }


        return binding.root
    }



    private fun addEstados() {
        val estado = binding.etEstado.text.toString()
        val capital  = binding.etCapitalEstado.text.toString()
        val poblacion = binding.etPoblacionEstado.text.toString()
        val costas  = binding.etCostasEstado.text.toString()

        if (estado.isNotEmpty()){
            val estados = Estados(0,estado,capital,poblacion,costas)
            estadosViewModel.saveEstados(estados)
            Toast.makeText(requireContext(),getString(R.string.msg_estado_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addEstadosFragment_to_nav_estados)
        }else {
            Toast.makeText(requireContext(),getString(R.string.msg_data),
                Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}