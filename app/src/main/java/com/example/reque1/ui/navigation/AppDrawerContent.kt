package com.example.reque1.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawerContent(
    currentDestination: AppDestination,
    onDestinationSelected: (AppDestination) -> Unit
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(12.dp))
        AppDestination.entries.forEach { destination ->
            NavigationDrawerItem(
                label = { Text(destination.label) },
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                selected = destination == currentDestination,
                onClick = { onDestinationSelected(destination) },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}