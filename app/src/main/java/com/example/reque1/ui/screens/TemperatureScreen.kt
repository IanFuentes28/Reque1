package com.example.reque1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.reque1.connection.ConnectivityObserver
import com.example.reque1.ui.components.ConnectionStatus
import com.example.reque1.ui.components.SaveButton
import com.example.reque1.ui.components.TemperatureInputField
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.reque1.gps.LocationProvider
import com.example.reque1.ui.components.RegisterLecturaButton


@Composable
fun TemperatureScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current //accede al context de android
    //crea el connectivity observer, el remember hace que se reutilice en lugar de crear nuevos
    val connectivityObserver = remember { ConnectivityObserver(context) }
    //crea el location provider
    val locationProvider = remember { LocationProvider(context) }


    //mutableStateOf presta atención a los valores cambiantes
    //rememberSaveable restaura el estado luego de cambios, optimiza
    //by delega propiedades, para reescribir atributos desde fuera
    var isConnected by rememberSaveable { mutableStateOf(false) }
    var temperature by rememberSaveable { mutableStateOf("") }


    //permisos
    var hasLocationPermission by remember {
        mutableStateOf(locationProvider.hasLocationPermission()) }


    val permissionLauncher = rememberLauncherForActivityResult( //recibe resultados, emite un flow
        contract = ActivityResultContracts.RequestPermission() //pide un solo permiso
    ) { granted ->
        hasLocationPermission = granted //reacciona a la autorización o negación del usuario, recibe la respuesta
    }


    //threats
    LaunchedEffect(Unit) { //emite un coroutine, unit hace que solo se haga una vez
        connectivityObserver.observe().collect { connected ->// collect activa el flow del connectivityObserver
            isConnected = connected
        }
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
            SaveButton(
                onClick = { /*temperature*/ },
                enabled = temperature.isNotBlank() //tiene que tener algo para guardar
            )
            RegisterLecturaButton( //crea el boton de lectura
                hasLocationPermission = hasLocationPermission, //pasa el permiso
                onRequestPermission = {
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION) //para pedir permiso
                },
                onRegister = {
                    //coroutine
                }
            )
        } //cierre de cosas que ocupan wifi
        //más cosas sin wifi

    }
}