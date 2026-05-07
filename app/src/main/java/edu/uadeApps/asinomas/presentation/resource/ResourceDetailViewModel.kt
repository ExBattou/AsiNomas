package edu.uadeApps.asinomas.presentation.resource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.uadeApps.asinomas.data.repository.FilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResourceDetailViewModel(
    private val repository: FilmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResourceDetailUiState(isLoading = true))
    val uiState: StateFlow<ResourceDetailUiState> = _uiState.asStateFlow()

    fun load(url: String) {
        viewModelScope.launch {
            _uiState.value = ResourceDetailUiState(isLoading = true)
            _uiState.value = runCatching {
                val data = repository.getResourceByUrl(url)
                ResourceDetailUiState(
                    title = data.name ?: data.title ?: "Resource",
                    url = data.url,
                    created = data.created.orEmpty(),
                    edited = data.edited.orEmpty(),
                    isFavorite = repository.isFavorite(data.url)
                )
            }.getOrElse { throwable ->
                ResourceDetailUiState(error = throwable.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite() {
        val current = _uiState.value
        if (current.url.isBlank()) return
        repository.toggleFavorite(current.url)
        _uiState.value = current.copy(isFavorite = !current.isFavorite)
    }

    class Factory(private val repository: FilmsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ResourceDetailViewModel(repository) as T
        }
    }
}