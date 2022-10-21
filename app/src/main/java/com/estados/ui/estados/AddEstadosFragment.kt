package com.lugares_v.ui.lugar

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
import com.lugares_v.R
import com.lugares_v.databinding.FragmentAddLugarBinding
import com.lugares_v.model.Estados
import com.lugares_v.viewmodel.EstadosViewModel

class AddEstadosFragment : Fragment() {
    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var estadosViewModel: EstadosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        estadosViewModel =  ViewModelProvider(this).get(EstadosViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)

        binding.btAddLugar.setOnClickListener { addLugar() }

        activaGPS()

        return binding.root
    }

    private fun activaGPS() {

        if(requireActivity()
                .checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            requireActivity()
                .checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            requireActivity().requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.ACCESS_FINE_LOCATION),
                105)
        }else{

            val  fusedLocationCLient:FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationCLient.lastLocation.addOnSuccessListener {
                location: Location? ->
                if(location != null) {
                    binding.tvLatitud.text = "${location.latitude}"
                    binding.tvLongitud.text = "${location.longitude}"
                    binding.tvAltura.text = "${location.altitude}"
                }else {

                    binding.tvLatitud.text = "0.0"
                    binding.tvLongitud.text = "0.0"
                    binding.tvAltura.text = "0.0"

                }
            }

        }

    }

    private fun addLugar() {
        val nombre = binding.etNombre.text.toString()
        val correo  = binding.etCorreoLugar.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web  = binding.etWeb.text.toString()
        val latitud = binding.tvLatitud.toString().toDouble()
        val longitud  = binding.tvLongitud.toString().toDouble()
        val altura = binding.tvAltura.toString().toDouble()

        if (nombre.isNotEmpty()){
            val estados = Estados(0,nombre,correo,web,telefono,latitud,longitud,altura,"","")
            estadosViewModel.saveLugar(estados)
            Toast.makeText(requireContext(),getString(R.string.msg_lugar_added),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
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