package com.example.catalogviewer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.catalogviewer.R
import com.example.catalogviewer.domain.model.Book

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit,
    onFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${book.category} • $${book.price}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(text = "⭐ ${book.rating}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    imageVector = if (book.isFavorite) ImageVector.vectorResource(R.drawable.ic_favorite) else ImageVector.vectorResource(R.drawable.ic_favorite_outlined),
                    contentDescription = "Toggle Favorite",
                    tint = if (book.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}