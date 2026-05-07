package edu.uadeApps.asinomas.presentation.detail

import edu.uadeApps.asinomas.domain.Film
import edu.uadeApps.asinomas.domain.ResourceItem

data class FilmDetailUiState(
    val isLoading: Boolean = false,
    val film: Film? = null,
    val characters: List<ResourceItem> = emptyList(),
    val planets: List<ResourceItem> = emptyList(),
    val starships: List<ResourceItem> = emptyList(),
    val vehicles: List<ResourceItem> = emptyList(),
    val species: List<ResourceItem> = emptyList(),
    val error: String? = null,
    val favoriteMap: Map<String, Boolean> = emptyMap()
)