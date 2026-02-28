package com.example.catalogviewer.presentation.navigation

import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.example.catalogviewer.presentation.catalog.CatalogScreen
import com.example.catalogviewer.presentation.MainViewModel
import com.example.catalogviewer.presentation.detail.BookDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object CatalogList: NavKey

@Serializable
data class BookDetail(val bookId: String): NavKey

internal fun entryProvider(
    viewModel: MainViewModel
) = entryProvider {

    entry<CatalogList> {
        CatalogScreen(
            viewModel = viewModel,
        )
    }

    entry<BookDetail> {
        BookDetailScreen(
            bookId = it.bookId,
            viewModel = viewModel,
        )
    }
}
