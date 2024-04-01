package com.example.resources.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopLoader(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().height(4.dp), contentAlignment = Alignment.Center) {

        LinearProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}