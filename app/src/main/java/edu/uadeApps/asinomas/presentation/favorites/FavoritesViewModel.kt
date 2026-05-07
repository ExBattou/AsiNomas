package edu.uadeApps.asinomas.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.uadeApps.asinomas.data.repository.FilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FilmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesUiState(isLoading = true))
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = FavoritesUiState(isLoading = true)
            _uiState.value = runCatching {
                val ids = repository.getAllFavoriteIds()
                FavoritesUiState(items = repository.resolveResources(ids))
            }.getOrElse { throwable ->
                FavoritesUiState(error = throwable.message ?: "Unknown error")
            }
        }
    }

    fun removeFavorite(id: String) {
        if (repository.isFavorite(id)) {
            repository.toggleFavorite(id)
        }
        _uiState.value = _uiState.value.copy(items = _uiState.value.items.filterNot { it.url == id })
    }

    class Factory(private val repository: FilmsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(repository) as T
        }
    }
}