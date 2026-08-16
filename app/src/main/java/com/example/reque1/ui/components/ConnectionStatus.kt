package com.example.reque1.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


// componente que muestra el estado de conexión a internet
@Composable
fun ConnectionStatus(
    isConnected: Boolean, //recibe el flow status del connectivity observer
    modifier: Modifier = Modifier //para modificar el componete a gusto
) {
    if (isConnected) { //actua como while porque recibe un flow
        Text( //composable, así que se printea como ui
            text = "Conectado a internet", //mientras que el flow sea true
            modifier = modifier //modificaciones
        )
    } else {
        Text(
            text = "Sin conexion a internet", //flow false
            color = MaterialTheme.colorScheme.error, //por cambios de colores
            modifier = modifier //modifc
        )
    }
}