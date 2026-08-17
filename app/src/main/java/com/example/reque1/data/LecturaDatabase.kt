package com.example.reque1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//recibe las entidades y los Dao, es el que junta

//entities = [LecturaEntity::class] recibe las tablas/clases de la base de datos
//version = 1; tiene que incrementar si se modifican entitys con las que ya trabajan
//export schema; para exportar en json, de momento mejor no
@Database(entities = [LecturaEntity::class], version = 1, exportSchema = false)
abstract class LecturaDatabase : RoomDatabase() { //room hace toda la vuelta, así que basta con abstract

    //database.lecturaDao() para llamarlo, devuelve también insertar() y obtenerTodas()
    abstract fun lecturaDao(): LecturaDao //room lo implementa solo

    //singleton
    companion object { //equivalente a static, código que pertenece a la clase, no a una instancia
        @Volatile //refresca automáticamente cualquier corutina que la utilice
        private var INSTANCE: LecturaDatabase? = null

        fun getDatabase(context: Context): LecturaDatabase {
            return INSTANCE ?: synchronized(this) { //comprueba si ya existe, antes de entrar en synchronized()
                val instance = Room.databaseBuilder( //crea la base de datos
                    context.applicationContext, //retoma memoria vieja, evita lagunas de memoria
                    LecturaDatabase::class.java, //cuál base de datos crear, con que clase
                    "lecturas_database" //nombre del archivo, lo dejo hardcodeado
                ).build() //ahora si, construye
                INSTANCE = instance //la sincroniza
                instance
            }
        }
    }
}