package edu.uadeApps.asinomas.presentation.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.uadeApps.asinomas.data.repository.FilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val repository: FilmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilmsUiState(isLoading = true))
    val uiState: StateFlow<FilmsUiState> = _uiState.asStateFlow()

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            _uiState.value = FilmsUiState(isLoading = true)
            _uiState.value = runCatching {
                FilmsUiState(films = repository.getFilms())
            }.getOrElse { throwable ->
                FilmsUiState(error = throwable.message ?: "Unknown error")
            }
        }
    }

    class Factory(private val repository: FilmsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilmsViewModel(repository) as T
        }
    }
}