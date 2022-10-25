package com.estados.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.estados.databinding.EstadosFilaBinding
import com.estados.model.Estados
import com.estados.ui.estados.EstadosFragmentDirections

class EstadosAdapter : RecyclerView.Adapter<EstadosAdapter.EstadosViewHolder>() {
    //La lista de lugares a "dibujar"
    private var listaEstados = emptyList<Estados>()
    //Contenedor de vistas "cajitas" en memoria...
    inner class EstadosViewHolder(private val itemBinding: EstadosFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun dibuja(estados: Estados) {
            itemBinding.tvNombre.text = estados.estado
            itemBinding.tvCapital.text = estados.capital
            itemBinding.tvPoblacion.text = estados.poblacion
            itemBinding.tvCostas.text = estados.costas
            itemBinding.vistaFila.setOnClickListener {
                val accion = EstadosFragmentDirections
                    .actionNavEstadosToUpdateEstadosFragment(estados)
                itemView.findNavController().navigate(accion)
            }

        }
    }
    //Crea un "cajita" una vista del tipo lugarFila...
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadosViewHolder {
        val itemBinding = EstadosFilaBinding
            .inflate(LayoutInflater.from(parent.context)
                ,parent
                ,false)
        return EstadosViewHolder(itemBinding)
    }
    //Con una "cajita" creada... se pasa a dibujar los datos del lugar x
    override fun onBindViewHolder(holder: EstadosViewHolder, position: Int) {
        val lugarEstado = listaEstados[position]
        holder.dibuja(lugarEstado)
    }
    override fun getItemCount(): Int {
        return listaEstados.size
    }
    fun setEstados(estados : List<Estados>) {
        listaEstados = estados
        notifyDataSetChanged()  //Se notifica que el conjunto de datos cambio y se redibuja toda la lista
    }
}