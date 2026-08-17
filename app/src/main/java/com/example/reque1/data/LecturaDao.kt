package com.example.reque1.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao //define operaciones aplicables a la tabla
interface LecturaDao { //room genera el sql

    @Insert //genera automáticamente el insert según atributo
    suspend fun insertar(lectura: LecturaEntity): Long //suspend para que siempre se haga corutina

    @Query("SELECT * FROM lecturas_temperatura ORDER BY fechaHora DESC") //trae todas las columnas de todas las filas por orden de fecha
    fun obtenerTodas(): Flow<List<LecturaEntity>> //por la room, se dispara automáticamente al notar cambios
}