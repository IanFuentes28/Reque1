package com.example.reque1.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


//boton de registrar lecturas
@Composable
fun RegisterLecturaButton(
    hasLocationPermission: Boolean, //permiso de gps
    onRequestPermission: () -> Unit, //callback
    //delega la solicitud de permiso al componente padre si no hay permiso

    onRegister: () -> Unit, //guarda la lectura y coordenadas
    modifier: Modifier = Modifier //modificadores
) {
    Button(
        onClick = { //ejecuta si hay permiso, si no llama a onRequestPermission
            if (!hasLocationPermission) {
                onRequestPermission()
            } else {
                onRegister() //si sí, guarda
            }
        },
        modifier = modifier //modificadores
    ) {
        Text("Registrar Lectura") //texto de acompañamiento
    }
}