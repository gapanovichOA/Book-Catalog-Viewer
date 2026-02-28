package com.example.catalogviewer.domain.repository

import com.example.catalogviewer.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun getBooks(query: String): Flow<List<Book>>
    suspend fun toggleFavorite(bookId: String)
}