package com.example.catalogviewer.presentation.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.catalogviewer.presentation.UiIntent
import com.example.catalogviewer.presentation.MainViewModel
import com.example.catalogviewer.presentation.components.BookItem
import com.example.catalogviewer.presentation.components.EmptyState
import com.example.catalogviewer.presentation.components.SearchBar

@Composable
fun CatalogScreen(
    viewModel: MainViewModel,
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(state.searchQuery) {
            viewModel.onIntent(UiIntent.Search(it))
        }

        if (state.items.isEmpty() && !state.isLoading) {
            EmptyState()
        } else {
            LazyColumn {
                items(state.items) { book ->
                    BookItem(
                        book = book,
                        onClick = {
                            viewModel.onIntent(UiIntent.SelectBook(book))
                        },
                        onFavorite = { viewModel.onIntent(UiIntent.ToggleFavorite(book.id)) }
                    )
                }
            }
        }
    }
}
