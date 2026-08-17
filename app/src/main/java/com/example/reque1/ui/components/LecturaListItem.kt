package com.example.reque1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reque1.data.LecturaEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LecturaListItem(
    lectura: LecturaEntity, //pasa objeto desde la room
    modifier: Modifier = Modifier //modificadores
) {
    val formatoFecha = remember { //para no rehacerlo cada que se llama recompone la pantalla
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) //formatea fechas
    }

    Card( //da forma a las lecturas
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) //configuración visual
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${lectura.temperatura} °C", //expone la temperatura
                style = MaterialTheme.typography.titleMedium //estilo del parámetro
            )
            Text(
                text = "Lat: ${lectura.latitud}, Long: ${lectura.longitud}", //expone gps
                style = MaterialTheme.typography.bodySmall //estilo del parámetro
            )
            Text(
                text = formatoFecha.format(Date(lectura.fechaHora)), //expone fecha y hora
                style = MaterialTheme.typography.bodySmall //estilo del parámetro
            )
        }
    }
}