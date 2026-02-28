package com.example.catalogviewer.domain.model

data class Book(
    val id: String,
    val title: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val isFavorite: Boolean = false
)