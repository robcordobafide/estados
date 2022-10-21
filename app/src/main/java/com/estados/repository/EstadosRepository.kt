package com.estados.repository

import androidx.lifecycle.LiveData
import com.estados.data.EstadosDao
import com.estados.model.Estados
class EstadosRepository(private val estadosDao: EstadosDao) {

    suspend fun saveEstados(estados: Estados) {
        if (estados.id==0) {  //Es un lugar nuevo
            estadosDao.addEstados(estados)
        } else {  //El lugar existe... entonces se actualiza...
            estadosDao.updateEstados(estados)
        }
    }
    suspend fun deleteEstados(estados: Estados) {
        estadosDao.deleteEstados(estados)
    }
    val getEstados : LiveData<List<Estados>> = estadosDao.getEstados()
}