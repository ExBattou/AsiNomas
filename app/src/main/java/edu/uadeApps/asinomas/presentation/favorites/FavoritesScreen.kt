package edu.uadeApps.asinomas.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun FavoritesScreen(
    viewModel: FavoritesViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF111319), Color(0xFF1B1726), Color.Black)
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Favoritos",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )

        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text(text = "Error: ${state.error}", color = Color.White)
            state.items.isEmpty() -> Text(text = "No hay favoritos guardados todavía.", color = Color.White)
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(state.items, key = { it.url }) { item ->
                        FavoriteRow(
                            title = item.label,
                            isFavorite = true,
                            onFavoriteClick = { viewModel.removeFavorite(item.url) }
                        )
                    }
                }
            }
        }
    }
}