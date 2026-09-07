package com.example.reque1.ui.pantallas

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reque1.ui.components.LocationPermissionRequest
import com.example.reque1.ui.components.RecenterButton
import com.example.reque1.ui.viewmodel.MapsViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    viewModel: MapsViewModel = viewModel()
) {
    var hasLocationPermission by remember {
        mutableStateOf(viewModel.hasLocationPermission())
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasLocationPermission = granted
        if (granted) viewModel.actualizarUbicacion()
    }

    if (!hasLocationPermission) {
        LocationPermissionRequest(
            onRequestPermission = {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            },
            modifier = modifier
        )
        return
    }

    val currentLocation by viewModel.currentLocation.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        viewModel.actualizarUbicacion()
    }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 16f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            currentLocation?.let {
                Marker(state = MarkerState(position = it), title = "Tu ubicación")
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        RecenterButton(
            onClick = { viewModel.actualizarUbicacion() },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        )
    }
}