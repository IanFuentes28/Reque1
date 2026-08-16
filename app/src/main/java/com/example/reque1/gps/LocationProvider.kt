package com.example.reque1.gps

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationProvider(private val context: Context) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context) //es el que consigue la ubi mediante google play services
        //también se puede usar android manager, pero más jodido
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission( //revisa si hay permiso otorgado
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION //donde se encuentra el permiso
        ) == PackageManager.PERMISSION_GRANTED //comprueba tenerlo y devuelve booleano
    }


    //suspend fun es para que solo sea llamada desde corutinas u otra suspend fun
    @SuppressLint("MissingPermission") //para evitar que el programa te tire error o advertencia
    suspend fun getCurrentLocation(): Location? {
        if (!hasLocationPermission()) return null //por si se llega a llamar la función sin permiso

        return suspendCancellableCoroutine { continuation -> //pausa el thread hasta que se dispare un callback
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, //busca la ubicación actual, aplica precisión
                null //si algo falla devuelve null, evita caerse
            ).addOnSuccessListener { location -> //
                continuation.resume(location) //reanuda con locación
            }.addOnFailureListener {
                continuation.resume(null) //cancela
            }
        }
    }
}