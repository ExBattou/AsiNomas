package edu.uadeApps.asinomas.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import edu.uadeApps.asinomas.domain.Film

@Composable
fun FilmPosterCard(
    film: Film,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = film.posterUrl),
                contentDescription = film.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Text(
            text = film.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}