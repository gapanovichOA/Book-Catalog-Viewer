package com.example.catalogviewer.data.repository

import com.example.catalogviewer.data.local.BookDao
import com.example.catalogviewer.data.local.FavoriteEntity
import com.example.catalogviewer.data.remote.JsonProvider
import com.example.catalogviewer.domain.model.Book
import com.example.catalogviewer.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val localDataSource: BookDao,
    private val jsonProvider: JsonProvider
) : CatalogRepository {

    override fun getBooks(query: String): Flow<List<Book>> {
        return localDataSource.getFavoriteIds().map { favoriteIds ->
            jsonProvider.loadCatalog()
                .filter { it.title.contains(query, ignoreCase = true) }
                .map { book ->
                    book.copy(isFavorite = favoriteIds.contains(book.id))
                }
        }
    }

    override suspend fun toggleFavorite(bookId: String) {
        val isFav = localDataSource.isFavorite(bookId)
        if (isFav) {
            localDataSource.deleteFavorite(bookId)
        } else {
            localDataSource.insertFavorite(FavoriteEntity(bookId))
        }
    }
}