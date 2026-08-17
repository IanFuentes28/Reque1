package com.example.reque1.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reque1.ui.components.ConnectionStatus
import com.example.reque1.ui.components.RegisterLecturaButton
import com.example.reque1.ui.components.TemperatureInputField
import com.example.reque1.ui.viewmodel.LecturaViewModel

@Composable
fun TemperatureScreen(
    modifier: Modifier = Modifier, //modificadores
    viewModel: LecturaViewModel = viewModel() //recibe el view model
) {

    //mutableStateOf presta atención a los valores cambiantes
    //rememberSaveable restaura el estado luego de cambios, optimiza
    //by delega propiedades, para reescribir atributos desde fuera
    var temperature by rememberSaveable { mutableStateOf("") }

    //el viewModel expone un StateFlow
    //collectAsState() lo convierte en State de Compose automáticamente
    val isConnected by viewModel.isConnected.collectAsState()

    //permisos
    //se consulta al viewModel
    var hasLocationPermission by remember {
        mutableStateOf(viewModel.hasLocationPermission())
    }

    val permissionLauncher = rememberLauncherForActivityResult( //recibe resultados, emite un flow
        contract = ActivityResultContracts.RequestPermission() //pide un solo permiso
    ) { granted ->
        hasLocationPermission = granted //reacciona a la autorización o negación del usuario, recibe la respuesta
    }

    Column( //ordena verticalmente los componentes
        modifier = modifier //modificar
            .fillMaxWidth() //ocupa el ancho disponible y enmarca con 24.dp
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), //16.dp entre elementos
        horizontalAlignment = Alignment.CenterHorizontally //centra horizontalmente los elementos
    ) {
        //primer elemento del column
        //siempre se muestra, tiene dos posibles estados diferentes
        ConnectionStatus(isConnected = isConnected)

        if (isConnected) { //los otros elementos solo se muestran si hay internet
            TemperatureInputField( //campo de temperatura
                value = temperature, //pasa la temperatura
                onValueChange = { temperature = it } //re actualiza si hay cambio en temperatura
                //la validación de cambios están en el componente
            )
            RegisterLecturaButton( //crea el boton de lectura
                hasLocationPermission = hasLocationPermission, //pasa el permiso
                onRequestPermission = {
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) //para pedir permiso
                },
                onRegister = {
                    //convierte el texto a Double
                    //si el campo no es un número válido simplemente no hace nada
                    val valor = temperature.toDoubleOrNull()
                    if (valor != null) {
                        //el viewModel obtiene el GPS y guarda en Room; onResult avisa si salió bien
                        viewModel.registrarLectura(valor) { exito -> //guarda
                            if (exito) temperature = "" //limpia el campo solo si sí se guardó
                        }
                    }
                }
            )
        } //cierre de cosas que ocupan wifi
        //más cosas sin wifi

    }
}