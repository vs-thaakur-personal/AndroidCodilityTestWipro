package com.example.dashboard.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun CategoryList(categories: List<String>, onCategoryClicked: (String) -> Unit) {
    LazyRow {
        items(categories.size) { index ->
            val category = categories[index]
            CategoryItem(name = category, onCategoryClicked = onCategoryClicked)
        }
    }
}

@Composable
fun CategoryItem(name: String, onCategoryClicked: (String) -> Unit) {
    Card(
        modifier = Modifier.padding(10.dp).clickable { onCategoryClicked(name) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
        ),
    ) {
        Text(
            text = name.replaceFirstChar { it.uppercaseChar() },
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}