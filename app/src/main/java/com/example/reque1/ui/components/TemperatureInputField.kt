package com.example.reque1.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TemperatureInputField(
    value: String, //texto que se muestra
    onValueChange: (String) -> Unit, //no devuelve nada
    modifier: Modifier = Modifier //igual para modificar
) {
    OutlinedTextField( //parametros
        value = value, //lo que se le ingrese
        onValueChange = { newValue -> //solo guarda si cumple el if
            // Solo permite números, punto decimal y signo negativo
            if (newValue.matches(Regex("^-?\\d*\\.?\\d*\$"))) {
                onValueChange(newValue) // ? indica que es opcional, d* numeros
            }
        },
        label = { Text("Temperatura") }, //texto base
        //genera teclado numerico
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier.fillMaxWidth()
        //recibe cualquier modifier de afuera y extiende al ancho
    )
}