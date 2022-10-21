package com.lugares_v.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares_v.data.EstadosDatabase
import com.lugares_v.model.Estados
import com.lugares_v.repository.EstadosRepository
import kotlinx.coroutines.launch
class EstadosViewModel(application: Application) : AndroidViewModel(application) {
    val getLugares : LiveData<List<Estados>>
    private val repository: EstadosRepository
    init {
        val lugarDao = EstadosDatabase.getDatabase(application).lugarDao()
        repository = EstadosRepository(lugarDao)
        getLugares = repository.getLugares
    }
    fun saveLugar(estados: Estados) {
        viewModelScope.launch { repository.saveLugar(estados) }
    }
    fun deleteLugar(estados: Estados) {
        viewModelScope.launch { repository.deleteLugar(estados)}
    }
}