package com.example.catalogviewer.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    val title: String,
    val category: String,
    val price: Double,
    val rating: Double
)