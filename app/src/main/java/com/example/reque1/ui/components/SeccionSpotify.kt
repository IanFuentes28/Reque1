package com.example.reque1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.reque1.music.ControladorSpotify

@Composable
fun SpotifySection(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var playlistInput by remember { mutableStateOf("") }
    val ControladorSpotify = remember { ControladorSpotify(context) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reproductor de Spotify")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = playlistInput,
            onValueChange = { playlistInput = it },
            label = { Text("Link o ID de la Playlist") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (playlistInput.isNotBlank()) {
                    ControladorSpotify.reproducirPlaylist(playlistInput)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reproducir en Spotify")
        }
    }
}