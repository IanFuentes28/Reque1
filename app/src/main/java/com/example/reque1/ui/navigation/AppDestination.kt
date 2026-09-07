package com.example.reque1.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.reque1.ui.components.SpotifySection //al reves por algun motivo
import com.example.reque1.ui.pantallas.MapsScreen
import com.example.reque1.ui.screens.TemperatureScreen

enum class AppDestination(
    val label: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit
) {
    TEMPERATURA("Temperatura", Icons.Filled.Thermostat, { TemperatureScreen() }),
    MUSICA("Música", Icons.Filled.MusicNote, { SpotifySection() }),
    MAPA("Mapa", Icons.Filled.Map, { MapsScreen() })
}