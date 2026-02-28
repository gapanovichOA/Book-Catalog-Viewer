package com.example.catalogviewer.presentation

import com.example.catalogviewer.domain.model.Book

sealed class UiIntent {
    data class Search(val query: String) : UiIntent()
    data class ToggleFavorite(val id: String) : UiIntent()
    data class SelectBook(val book: Book) : UiIntent()
    data object ClearError : UiIntent()
}
