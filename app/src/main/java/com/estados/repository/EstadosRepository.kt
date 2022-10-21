package com.lugares_v.repository

import androidx.lifecycle.LiveData
import com.lugares_v.data.EstadosDao
import com.lugares_v.model.Estados
class EstadosRepository(private val estadosDao: EstadosDao) {
    suspend fun saveLugar(estados: Estados) {
        if (estados.id==0) {  //Es un lugar nuevo
            estadosDao.addLugar(estados)
        } else {  //El lugar existe... entonces se actualiza...
            estadosDao.updateLugar(estados)
        }
    }
    suspend fun deleteLugar(estados: Estados) {
        estadosDao.deleteLugar(estados)
    }
    val getLugares : LiveData<List<Estados>> = estadosDao.getLugares()
}