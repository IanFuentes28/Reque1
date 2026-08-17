package com.example.reque1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.reque1.connection.ConnectivityObserver
import com.example.reque1.gps.LocationProvider
import com.example.reque1.data.LecturaDatabase
import com.example.reque1.data.LecturaEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


// AndroidViewModel permite trabajar con context
class LecturaViewModel(application: Application) : AndroidViewModel(application) {


    //AndroidViewModel los contruye, se les pasa toda la info mediante context=application
    //privados porque igual la ui solo ocupa la info del view model, no la variable como tal
    private val connectivityObserver = ConnectivityObserver(application)
    private val locationProvider = LocationProvider(application)
    private val lecturaDao = LecturaDatabase.getDatabase(application).lecturaDao()

    //emite un unico flow, todos trabajan con el mismo flow
    val isConnected: StateFlow<Boolean> = connectivityObserver.observe()
        .stateIn(//convierte de flow a stateflow
            scope = viewModelScope,//para matar cualquier corutina si el viewmodel muere
            started = SharingStarted.WhileSubscribed(5000), //espera 5 segundos antes de detener elflow
            initialValue = false //valor inicial del flow, previo a la primera emision
        )

    //emite un unico flow, todos trabajan con el mismo flow
    val lecturas: StateFlow<List<LecturaEntity>> = lecturaDao.obtenerTodas()
        .stateIn( //convierte de flow a stateflow
            scope = viewModelScope, //para matar cualquier corutina si el viewmodel muere
            started = SharingStarted.WhileSubscribed(5000), //espera 5 segundos antes de detener el flow
            initialValue = emptyList() //valor inicial del flow, previo a la primera emision
        )

    //llama para preguntar por permiso de gps
    fun hasLocationPermission(): Boolean = locationProvider.hasLocationPermission()
    //función para registrar temperatura, la temperatura entra como parametro
    fun registrarLectura(temperatura: Double, onResult: (exito: Boolean) -> Unit) {
        viewModelScope.launch { //instancia corutina
            val location = locationProvider.getCurrentLocation() //guarda gps
            if (location != null) { //pregunta si registro gps de forma correcta
                lecturaDao.insertar( //inserta el objeto/lectura en la Room
                    LecturaEntity( //definición del objeto
                        temperatura = temperatura, //parametro tal cual, validado desde antes
                        latitud = location.latitude, //define latitud
                        longitud = location.longitude //defien longitud
                    )
                )
                onResult(true) //emite una señal de que se guardó correctamente
            } else {
                onResult(false) //en caso de que fallara, también lo emite
            }
        }
    }
}