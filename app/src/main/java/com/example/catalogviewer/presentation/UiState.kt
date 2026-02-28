package com.example.catalogviewer.presentation

import com.example.catalogviewer.domain.model.Book

data class UiState(
    val items: List<Book> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedBook: Book? = null
)