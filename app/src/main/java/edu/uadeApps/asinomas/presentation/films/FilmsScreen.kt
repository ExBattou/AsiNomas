package edu.uadeApps.asinomas.presentation.films

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.uadeApps.asinomas.presentation.components.FilmPosterCard

@Composable
fun FilmsScreen(
    viewModel: FilmsViewModel,
    onFilmClick: (String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0E0E16), Color(0xFF1A1A2E), Color.Black)
                )
            )
            .padding(top = 56.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Star Wars Films",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TextButton(
            onClick = onFavoritesClick,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(text = "Ver Favoritos")
        }

        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            else -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(state.films) { film ->
                        FilmPosterCard(film = film) {
                            onFilmClick(film.url)
                        }
                    }
                }
            }
        }
    }
}