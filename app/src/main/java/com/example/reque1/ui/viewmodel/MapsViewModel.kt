package com.example.reque1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.reque1.gps.LocationProvider
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapsViewModel(application: Application) : AndroidViewModel(application) {

    private val locationProvider = LocationProvider(application)

    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation: StateFlow<LatLng?> = _currentLocation.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun hasLocationPermission(): Boolean = locationProvider.hasLocationPermission()

    fun actualizarUbicacion() {
        viewModelScope.launch {
            _isLoading.value = true
            val location = locationProvider.getCurrentLocation()
            if (location != null) {
                _currentLocation.value = LatLng(location.latitude, location.longitude)
            }
            _isLoading.value = false
        }
    }
}