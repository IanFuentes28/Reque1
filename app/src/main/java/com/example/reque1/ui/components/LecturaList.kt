package com.example.reque1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reque1.data.LecturaEntity

@Composable
fun LecturaList(
    lecturas: List<LecturaEntity>, //recibe la lista de objetos desde el viewmodel
    modifier: Modifier = Modifier //modea
) {
    if (lecturas.isEmpty()) { //cuando no hay nada en la room
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center //acomoda el composable centrado
        ) {
            Text("Aún no hay lecturas registradas")
        }
    } else {
        LazyColumn( //solo dibuja los que son visibles
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp) //ordena de forma vertical
        ) {
            items(lecturas, key = { it.id }) { lectura -> //recibe la lista, utiliza el id para elegir sobre quien iterar
                LecturaListItem(lectura = lectura) //va dibujando la lista
            }
        }
    }
}