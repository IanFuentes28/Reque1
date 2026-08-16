package com.example.reque1.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SaveButton(
    onClick: () -> Unit, //no recibe ni devuelve nada
    enabled: Boolean = true, //disponible por defecto
    modifier: Modifier = Modifier //modificadores
) {
    Button( //boton
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
    ) {
        Text("Guardar")
    }
}