package com.example.catalogviewer.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.catalogviewer.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: String,
    viewModel: MainViewModel,
) {
    val state by viewModel.state.collectAsState()

    val book = state.selectedBook ?: state.items.find { it.id == bookId }

    book?.let { item ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = item.title, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = item.category,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray
            )

            HorizontalDivider()

            Text(text = "Price: $${item.price}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Rating: ${item.rating} / 5.0", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Item ID: ${item.id}", style = MaterialTheme.typography.bodySmall)
        }
    } ?: run {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No book found")
        }
    }
}
