package edu.uadeApps.asinomas.presentation.resource

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.uadeApps.asinomas.presentation.components.FavoriteRow

@Composable
fun ResourceDetailScreen(
    resourceUrl: String,
    viewModel: ResourceDetailViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(resourceUrl) {
        viewModel.load(resourceUrl)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F1620), Color(0xFF1F1A2D), Color.Black)
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text(text = "Error: ${state.error}", color = Color.White)
            else -> {
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                FavoriteRow(
                    title = "Marcar como favorito",
                    isFavorite = state.isFavorite,
                    onFavoriteClick = { viewModel.toggleFavorite() }
                )
                Text(text = "URL: ${state.url}", color = Color.White)
                if (state.created.isNotBlank()) {
                    Text(text = "Created: ${state.created}", color = Color.White)
                }
                if (state.edited.isNotBlank()) {
                    Text(text = "Edited: ${state.edited}", color = Color.White)
                }
            }
        }
    }
}