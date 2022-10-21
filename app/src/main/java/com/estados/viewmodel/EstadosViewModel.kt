package com.estados.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.estados.data.EstadosDatabase
import com.estados.model.Estados
import com.estados.repository.EstadosRepository
import kotlinx.coroutines.launch
class EstadosViewModel(application: Application) : AndroidViewModel(application) {
    val getEstados : LiveData<List<Estados>>
    private val repository: EstadosRepository
    init {
        val estadosDao = EstadosDatabase.getDatabase(application).estadosDao()
        repository = EstadosRepository(estadosDao)
        getEstados = repository.getEstados
    }
    fun saveEstados(estados: Estados) {
        viewModelScope.launch { repository.saveEstados(estados) }
    }
    fun deleteEstados(estados: Estados) {
        viewModelScope.launch { repository.deleteEstados(estados)}
    }
}