package edu.uadeApps.asinomas.presentation.favorites

import edu.uadeApps.asinomas.domain.ResourceItem

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val items: List<ResourceItem> = emptyList(),
    val error: String? = null
)