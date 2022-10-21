package com.lugares_v.ui.lugar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lugares_v.R
import com.lugares_v.adapter.EstadosAdapter
import com.lugares_v.databinding.FragmentLugarBinding
import com.lugares_v.viewmodel.EstadosViewModel

class EstadosFragment : Fragment() {

    private var _binding: FragmentLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var estadosViewModel: EstadosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         estadosViewModel =  ViewModelProvider(this).get(EstadosViewModel::class.java)
        _binding = FragmentLugarBinding.inflate(inflater, container, false)

        binding.addLugarFabBt.setOnClickListener {
            findNavController().navigate(R.id.action_nav_lugar_to_addLugarFragment)
        }

        //activar el recyceler view
        val estadosAdapter = EstadosAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter= estadosAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        estadosViewModel.getLugares.observe(viewLifecycleOwner) {
            lugares -> estadosAdapter.setLugares(lugares)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}