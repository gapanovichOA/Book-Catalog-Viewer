package com.example.catalogviewer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogviewer.domain.repository.CatalogRepository
import com.example.catalogviewer.presentation.navigation.BookDetail
import com.example.catalogviewer.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CatalogRepository,
    private val navigationManager: NavigationManager,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState(isLoading = true))
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        observeBooks()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeBooks() {
        searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                repository.getBooks(query)
            }
            .onEach { items ->
                _state.update { it.copy(items = items, isLoading = false) }
            }
            .catch { e ->
                _state.update { it.copy(error = "Failed to load catalog", isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Search -> {
                _state.update { it.copy(searchQuery = intent.query) }
                searchQuery.value = intent.query
            }
            is UiIntent.ToggleFavorite -> {
                viewModelScope.launch {
                    repository.toggleFavorite(intent.id)
                }
            }
            is UiIntent.SelectBook -> {
                _state.update { it.copy(selectedBook = intent.book) }
                navigationManager.navigateTo(BookDetail(intent.book.id))
            }
            is UiIntent.ClearError -> {
                _state.update { it.copy(error = null) }
            }
        }
    }
}