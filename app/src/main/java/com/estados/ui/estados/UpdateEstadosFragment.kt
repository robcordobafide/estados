package com.estados.ui.estados

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.estados.R
import com.estados.databinding.FragmentUpdateEstadosBinding
import com.estados.model.Estados
import com.estados.viewmodel.EstadosViewModel

class UpdateEstadosFragment : Fragment() {


    //Se recupera un argumento

    //private val args by navArgs<UpdateEstadosFragmentArgs>()
    private val args by navArgs<UpdateEstadosFragmentArgs>()

    private var _binding: FragmentUpdateEstadosBinding? = null
    private val binding get() = _binding!!
    private lateinit var estadosViewModel: EstadosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        estadosViewModel =  ViewModelProvider(this).get(EstadosViewModel::class.java)
        _binding = FragmentUpdateEstadosBinding.inflate(inflater, container, false)

        binding.etEstadoUp.setText(args.estados.nombre)
        binding.etCapitalUp.setText(args.estados.correo)
        binding.etPoblacionUp.setText(args.estados.telefono)
        binding.etCostasUp.setText(args.estados.web)

        binding.btUpdateEstados.setOnClickListener { updateEstados() }
        binding.btDeleteEstados.setOnClickListener { deleteEstados() }

        return binding.root
    }


    private fun deleteEstados() {

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_estado)
        alerta.setMessage(getString(R.string.msg_estado_pregunta)+"${args.estados.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            estadosViewModel.deleteEstados(args.estados)
            Toast.makeText(requireContext(),getString(R.string.msg_estado_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateEstadosFragment_to_nav_estados)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)){_,_ ->}
        alerta.create().show()
    }

    private fun updateEstados() {
        val nombre = binding.etEstadoUp.text.toString()
        val correo  = binding.etCapitalUp.text.toString()
        val telefono = binding.etPoblacionUp.text.toString()
        val web  = binding.etCostasUp.text.toString()

        if (nombre.isNotEmpty()){
            val estados = Estados(args.estados.id,nombre,correo,web,telefono)
            estadosViewModel.saveEstados(estados)
            Toast.makeText(requireContext(),getString(R.string.msg_estado_updated),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateEstadosFragment_to_nav_estados)
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