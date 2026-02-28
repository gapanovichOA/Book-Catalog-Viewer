package com.example.catalogviewer.repository

import app.cash.turbine.test
import com.example.catalogviewer.data.local.BookDao
import com.example.catalogviewer.data.remote.JsonProvider
import com.example.catalogviewer.data.repository.CatalogRepositoryImpl
import com.example.catalogviewer.domain.model.Book
import com.example.catalogviewer.domain.repository.CatalogRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CatalogRepositoryTest {

    private val bookDao: BookDao = mockk()
    private val jsonProvider: JsonProvider = mockk()
    private lateinit var repository: CatalogRepository

    @Before
    fun setup() {
        repository = CatalogRepositoryImpl(bookDao, jsonProvider)
    }

    private val mockBooks = listOf(
        Book(id = "1", title = "Kotlin Basics", category = "Tech", price = 11.30, rating = 4.5, isFavorite = false),
        Book(id = "2", title = "Advanced Java", category = "Tech", price = 7.50, rating = 4.7, isFavorite = false)
    )

    @Test
    fun `getBooks should filter by query and map favorite status correctly`() = runTest {
        // GIVEN
        val query = "Kotlin"
        val favoriteIds = listOf("1")
        coEvery { bookDao.getFavoriteIds() } returns flowOf(favoriteIds)
        every { jsonProvider.loadCatalog() } returns mockBooks

        // WHEN - THEN
        repository.getBooks(query).test {
            val result = awaitItem()

            assertEquals(1, result.size)
            assertEquals("Kotlin Basics", result[0].title)
            assertTrue(result[0].isFavorite)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavorite should insert favorite when book is not currently a favorite`() = runTest {
        // GIVEN
        val bookId = "1"
        coEvery { bookDao.isFavorite(bookId) } returns false
        coEvery { bookDao.insertFavorite(any()) } just Runs

        // WHEN
        repository.toggleFavorite(bookId)

        // THEN
        coVerify { bookDao.insertFavorite(match { it.bookId == bookId }) }
        coVerify(exactly = 0) { bookDao.deleteFavorite(any()) }
    }

    @Test
    fun `toggleFavorite should delete favorite when book is already a favorite`() = runTest {
        // GIVEN
        val bookId = "1"
        coEvery { bookDao.isFavorite(bookId) } returns true
        coEvery { bookDao.deleteFavorite(bookId) } just Runs

        // WHEN
        repository.toggleFavorite(bookId)

        // THEN
        coVerify { bookDao.deleteFavorite(bookId) }
        coVerify(exactly = 0) { bookDao.insertFavorite(any()) }
    }
}