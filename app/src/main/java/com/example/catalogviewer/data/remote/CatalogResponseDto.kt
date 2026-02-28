package com.example.catalogviewer.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CatalogResponseDto(
    val updatedAt: String,
    val items: List<BookDto>
)