package edu.uadeApps.asinomas.presentation.resource

data class ResourceDetailUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val url: String = "",
    val created: String = "",
    val edited: String = "",
    val isFavorite: Boolean = false,
    val error: String? = null
)