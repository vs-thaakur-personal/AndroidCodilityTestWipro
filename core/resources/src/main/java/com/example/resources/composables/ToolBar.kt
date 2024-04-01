package com.example.resources.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(title: String, onNavigationIconClick: () -> Unit, showNavigationIcon: Boolean) {
    val navIcon: @Composable (() -> Unit)? = if (showNavigationIcon) {
        @Composable {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    } else {
        null
    }
    androidx.compose.material3.TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
        navigationIcon = navIcon?:{},
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        })
}