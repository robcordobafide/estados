package com.estados.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.estados.model.Estados
@Database(entities=[Estados::class], version = 1, exportSchema = false)
abstract class EstadosDatabase : RoomDatabase(){
    abstract fun estadosDao() : EstadosDao
    companion object {
        @Volatile
        private var INSTANCE: EstadosDatabase? = null
        fun getDatabase(context: android.content.Context) : EstadosDatabase {
            val temp = INSTANCE
            if (temp!=null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EstadosDatabase::class.java,
                    "estados_database"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}