package com.example.catalogviewer.data.remote

import android.util.Log
import com.example.catalogviewer.domain.model.Book
import kotlinx.serialization.json.Json

class JsonProvider() {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun loadCatalog(): List<Book> {
        return try {
            val response = json.decodeFromString<CatalogResponseDto>(RAW_CATALOG_JSON)

            response.items.map { dto ->
                Book(
                    id = dto.id,
                    title = dto.title,
                    category = dto.category,
                    price = dto.price,
                    rating = dto.rating,
                    isFavorite = false
                )
            }
        } catch (e: Exception) {
            Log.e("JsonProvider", "Error loading catalog: ${e.message}")
            emptyList()
        }
    }

    companion object {
        private const val RAW_CATALOG_JSON = """
            {
              "updatedAt": "2025-01-15T10:00:00Z",
              "items": [
                { "id": "bk_001", "title": "The Blue Fox", "category": "Fiction", "price": 12.99, "rating": 4.4 },
                { "id": "bk_002", "title": "Data Sketches", "category": "Non-Fiction", "price": 32.00, "rating": 4.8 },
                { "id": "bk_003", "title": "Swift Patterns", "category": "Tech", "price": 24.50, "rating": 4.1 },
                { "id": "bk_004", "title": "Kotlin by Example", "category": "Tech", "price": 21.00, "rating": 4.3 },
                { "id": "bk_005", "title": "Windswept", "category": "Fiction", "price": 14.25, "rating": 3.9 }
              ]
            }
            """
    }
}