package com.example.reque1.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri

class ControladorSpotify(private val context: Context) {

    fun reproducirPlaylist(input: String) {
        //limpia el texto y extrae el ID de la lista
        val playlistId = when {
            input.contains("open.spotify.com/playlist/") -> {
                input.substringAfter("playlist/").substringBefore("?")
            }
            input.startsWith("spotify:playlist:") -> {
                input.substringAfter("spotify:playlist:")
            }
            else -> input.trim()
        }

        // Abre la lista de reproducción directamente en la app de Spotify (o en el navegador)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/playlist/$playlistId")).apply {
            putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + context.packageName))
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    }
}