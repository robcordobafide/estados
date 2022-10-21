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

        binding.etNombre.setText(args.estados.nombre)
        binding.etCorreoEstados.setText(args.estados.correo)
        binding.etTelefono.setText(args.estados.telefono)
        binding.etWeb.setText(args.estados.web)

        binding.tvLatitud.text=args.estados.latitud.toString()
        binding.tvLongitud.text=args.estados.longitud.toString()
        binding.tvAltura.text=args.estados.altura.toString()

        binding.btUpdateEstados.setOnClickListener { updateEstados() }
        binding.btDeleteEstados.setOnClickListener { deleteEstados() }
        binding.btEmail.setOnClickListener { escribirCorreo() }
        binding.btPhone.setOnClickListener { llamarEstados() }
        binding.btWhatsapp.setOnClickListener { enviarWhatsApp() }
        binding.btWeb.setOnClickListener { verWeb() }
        binding.btLocation.setOnClickListener { verMapa() }

        return binding.root
    }

    private fun escribirCorreo() {
        val valor = binding.etCorreoEstados.text.toString()
        if(valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type="message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(valor))
            intent.putExtra(Intent.EXTRA_SUBJECT,
            getString(R.string.msg_saludos)+" "+binding.etNombre.text)
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.msg_mensaje_correo))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun llamarEstados() {
        val valor = binding.etTelefono.text.toString()
        if(valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$valor")
            if(requireActivity()
                    .checkSelfPermission(android.Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED){
                requireActivity().requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE),105)
            }else{
                requireActivity().startActivity(intent)
            }

        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun enviarWhatsApp() {
        val valor = binding.etTelefono.text.toString()
        if(valor.isNotEmpty()){
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = "whatsapp://send?phone=506$valor&text="+getString(R.string.msg_saludos)
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(uri)
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun verWeb() {
        val valor = binding.etWeb.text.toString()
        if(valor.isNotEmpty()){
            val uri = "http://$valor"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun verMapa() {
        val latitud = binding.tvLatitud.text.toString().toDouble()
        val longitud  = binding.tvLongitud.text.toString().toDouble()
        if(latitud.isFinite() && longitud.isFinite()){
            val uri = "geo:$latitud,$longitud?z18"
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(uri))
            startActivity(intent)
        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_data),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun deleteEstados() {

        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.bt_delete_estado)
        alerta.setMessage(getString(R.string.msg_estado_pregunta)+"${args.estados.nombre}?")
        alerta.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            estadosViewModel.deleteEstado(args.estados)
            Toast.makeText(requireContext(),getString(R.string.msg_estado_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateEstadosFragment_to_nav_estados)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)){_,_ ->}
        alerta.create().show()
    }

    private fun updateEstados() {
        val nombre = binding.etNombre.text.toString()
        val correo  = binding.etCorreoEstados.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web  = binding.etWeb.text.toString()

        if (nombre.isNotEmpty()){
            val estados = Estados(args.estados.id,nombre,correo,web,telefono,
                args.estados.latitud,
                args.estados.longitud,
                args.estados.altura,
                args.estados.rutaAudio,
                args.estados.rutaimagen)
            estadosViewModel.saveEstados(estados)
            Toast.makeText(requireContext(),getString(R.string.msg_estado_updated),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateEstadoFragment_to_nav_estado)
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