package com.example.reque1.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class ConnectivityObserver(context: Context) { // se pasa mediante LocalContext.current por compose

    private val connectivityManager = //connectitymanager hace toda la vuelta
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Flow<Boolean> es un flujo de datos que emite true o false segun lo que diga el metodo, en este caso, si se mantiene conectado
    // = callbackFlow para convertir el sistema callback del ConnectivityManager a flow para el flujo
    fun observe(): Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { //emite cuando se conecta a red
                trySend(isCurrentlyConnected()) //para comprobar si continua conectado
            }

            override fun onLost(network: Network) { //emite cuando una red deja de estar disponible
                trySend(isCurrentlyConnected()) //para comprobar si se desconecto la que utiliza
            }

            override fun onCapabilitiesChanged( //emite cuando hay un cambio en las propiedades de la red, como en el proceso de validación de señal
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                trySend(isCurrentlyConnected()) //para re comprobar si continua conectado
            }
        }

        val request = NetworkRequest.Builder().build() //identifica cambios en la red
        connectivityManager.registerNetworkCallback(request, callback)

        // Emitimos el estado inicial al empezar a observar
        trySend(isCurrentlyConnected())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback) //limpia cuando se cierra la conexión
        }
    }.distinctUntilChanged() //optimiza resultados repetidos

    private fun isCurrentlyConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false //devuelve una red disponible
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false //devuelve false si no hay red disponible
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && //revisa que el proevedor de inter si reporte estar activo
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) //valida que el sistema android si le funciona el internet
    }
}