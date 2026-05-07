package edu.uadeApps.asinomas.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.uadeApps.asinomas.data.repository.FilmsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmDetailViewModel(
    private val repository: FilmsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FilmDetailUiState(isLoading = true))
    val uiState: StateFlow<FilmDetailUiState> = _uiState.asStateFlow()

    fun load(url: String) {
        viewModelScope.launch {
            _uiState.value = FilmDetailUiState(isLoading = true)
            val film = repository.getFilmByUrl(url)
            if (film == null) {
                _uiState.value = FilmDetailUiState(error = "Film not found")
                return@launch
            }

            val characters = repository.resolveResources(film.characters)
            val planets = repository.resolveResources(film.planets)
            val starships = repository.resolveResources(film.starships)
            val vehicles = repository.resolveResources(film.vehicles)
            val species = repository.resolveResources(film.species)

            val allIds = buildList {
                add(film.url)
                addAll(characters.map { it.url })
                addAll(planets.map { it.url })
                addAll(starships.map { it.url })
                addAll(vehicles.map { it.url })
                addAll(species.map { it.url })
            }

            _uiState.value = FilmDetailUiState(
                film = film,
                characters = characters,
                planets = planets,
                starships = starships,
                vehicles = vehicles,
                species = species,
                favoriteMap = allIds.associateWith { repository.isFavorite(it) }
            )
        }
    }

    fun toggleFavorite(id: String) {
        repository.toggleFavorite(id)
        _uiState.value = _uiState.value.copy(
            favoriteMap = _uiState.value.favoriteMap.toMutableMap().apply {
                this[id] = !(this[id] ?: false)
            }
        )
    }

    class Factory(private val repository: FilmsRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FilmDetailViewModel(repository) as T
        }
    }
}