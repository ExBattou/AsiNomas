package edu.uadeApps.asinomas.presentation.films

import edu.uadeApps.asinomas.domain.Film

data class FilmsUiState(
    val isLoading: Boolean = false,
    val films: List<Film> = emptyList(),
    val error: String? = null
)