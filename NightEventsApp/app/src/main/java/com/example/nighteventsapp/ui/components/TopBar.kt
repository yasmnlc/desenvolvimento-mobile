package com.example.nighteventsapp.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.BrightnessHigh

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onThemeToggle: () -> Unit,
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        title = { Text("EventsApp") },
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) {
                Icon(Icons.Filled.Menu, contentDescription = "Open Menu")
            }
        },
        actions = {
            IconButton(onClick = onThemeToggle) {
                Icon(Icons.Filled.BrightnessHigh, contentDescription = "Toggle Theme")
            }
        }
    )
}
