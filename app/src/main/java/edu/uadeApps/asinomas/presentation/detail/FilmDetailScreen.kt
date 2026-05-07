package edu.uadeApps.asinomas.presentation.detail

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
fun FilmDetailScreen(
    filmUrl: String,
    viewModel: FilmDetailViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(filmUrl) {
        viewModel.load(filmUrl)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF120F1C), Color(0xFF151F2E), Color.Black)
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}", color = Color.White)
            state.film != null -> {
                val film = state.film

                Text(film?.title ?: "missing", style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
                film?.let {
                    FavoriteRow(
                        title = "Marcar pelicula favorita",
                        isFavorite = state.favoriteMap[it.url] == true,
                        onFavoriteClick = { viewModel.toggleFavorite(film.url) }
                    )
                }

                film?.let { Text("Director: ${it.director}", color = Color.White) }
                film?.let { Text("Producer: ${it.producer}", color = Color.White) }
                film?.let { Text("Release date: ${it.releaseDate}", color = Color.White) }
                film?.let { Text("Created: ${it.created}", color = Color.White) }
                film?.let { Text("Edited: ${it.edited}", color = Color.White) }
                film?.let { Text("URL: ${it.url}", color = Color.White) }

                SectionTitle("Characters")
                state.characters.forEach { item ->
                    FavoriteRow(
                        title = item.label,
                        isFavorite = state.favoriteMap[item.url] == true,
                        onFavoriteClick = { viewModel.toggleFavorite(item.url) }
                    )
                }

                SectionTitle("Planets")
                state.planets.forEach { item ->
                    FavoriteRow(
                        title = item.label,
                        isFavorite = state.favoriteMap[item.url] == true,
                        onFavoriteClick = { viewModel.toggleFavorite(item.url) }
                    )
                }

                SectionTitle("Starships")
                state.starships.forEach { item ->
                    FavoriteRow(
                        title = item.label,
                        isFavorite = state.favoriteMap[item.url] == true,
                        onFavoriteClick = { viewModel.toggleFavorite(item.url) }
                    )
                }

                SectionTitle("Vehicles")
                state.vehicles.forEach { item ->
                    FavoriteRow(
                        title = item.label,
                        isFavorite = state.favoriteMap[item.url] == true,
                        onFavoriteClick = { viewModel.toggleFavorite(item.url) }
                    )
                }

                SectionTitle("Species")
                state.species.forEach { item ->
                    FavoriteRow(
                        title = item.label,
                        isFavorite = state.favoriteMap[item.url] == true,
                        onFavoriteClick = { viewModel.toggleFavorite(item.url) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFFFFD54F),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 12.dp)
    )
}