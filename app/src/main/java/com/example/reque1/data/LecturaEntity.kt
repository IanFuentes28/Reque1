package com.example.reque1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lecturas_temperatura") //describe una tabla
data class LecturaEntity( //entity permite hacer comportar las clases como tablas, cada atributo pasa a ser columnas
    //hace la columna como la primary key
    @PrimaryKey(autoGenerate = true) val id: Int = 0, //genera id incrementales
    //datos por lectura
    val temperatura: Double,
    //lo obtienes mediante location provider
    val latitud: Double,
    val longitud: Double,

    val fechaHora: Long = System.currentTimeMillis() //saca fecha
)