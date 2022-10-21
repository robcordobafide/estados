package com.estados.ui.estados

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.estados.R
import com.estados.adapter.EstadosAdapter
import com.estados.databinding.FragmentEstadosBinding
import com.estados.viewmodel.EstadosViewModel

class EstadosFragment : Fragment() {

    private var _binding: FragmentEstadosBinding? = null
    private val binding get() = _binding!!
    private lateinit var estadosViewModel: EstadosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         estadosViewModel =  ViewModelProvider(this).get(EstadosViewModel::class.java)
        _binding = FragmentEstadosBinding.inflate(inflater, container, false)

        binding.addEstadosFabBt.setOnClickListener {
            findNavController().navigate(R.id.action_nav_estados_to_addEstadosFragment)
        }

        //activar el recyceler view
        val estadosAdapter = EstadosAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter= estadosAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        estadosViewModel.getEstados.observe(viewLifecycleOwner) {
                estados -> estadosAdapter.setEstados(estados)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}