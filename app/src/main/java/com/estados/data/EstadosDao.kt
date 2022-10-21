package com.estados.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.estados.model.Estados
@Dao
interface EstadosDao {
    //CRUD Create Read Update Delete
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEstados(estados: Estados)
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateEstados(estados: Estados)
    @Delete
    suspend fun deleteEstados(estados: Estados)
    @Query("SELECT * FROM ESTADOS")
    fun getEstados() : LiveData<List<Estados>>
}