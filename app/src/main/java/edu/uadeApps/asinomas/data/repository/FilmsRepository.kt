package edu.uadeApps.asinomas.data.repository

import edu.uadeApps.asinomas.data.local.FavoritesStore
import edu.uadeApps.asinomas.data.network.SwapiApi
import edu.uadeApps.asinomas.domain.Film
import edu.uadeApps.asinomas.domain.ResourceItem

class FilmsRepository(
    private val api: SwapiApi,
    private val favoritesStore: FavoritesStore
) {
    private var filmsCache: List<Film> = emptyList()

    suspend fun getFilms(): List<Film> {
        if (filmsCache.isNotEmpty()) return filmsCache

        filmsCache = api.getFilms()
            .sortedBy { it.episodeId }
            .map { dto ->
                Film(
                    title = dto.title,
                    episodeId = dto.episodeId,
                    openingCrawl = dto.openingCrawl,
                    director = dto.director,
                    producer = dto.producer,
                    releaseDate = dto.releaseDate,
                    characters = dto.characters,
                    planets = dto.planets,
                    starships = dto.starships,
                    vehicles = dto.vehicles,
                    species = dto.species,
                    created = dto.created,
                    edited = dto.edited,
                    url = dto.url,
                    posterUrl = posterForEpisode(dto.episodeId)
                )
            }
        return filmsCache
    }

    suspend fun getFilmByUrl(url: String): Film? {
        val list = getFilms()
        return list.firstOrNull { it.url == url }
    }

    suspend fun resolveResources(urls: List<String>): List<ResourceItem> {
        return urls.map { url ->
            runCatching {
                val data = api.getResource(url)
                ResourceItem(
                    label = data.name ?: data.title ?: url,
                    url = data.url
                )
            }.getOrElse {
                ResourceItem(label = url, url = url)
            }
        }
    }

    fun isFavorite(id: String): Boolean = favoritesStore.isFavorite(id)

    fun toggleFavorite(id: String) = favoritesStore.toggleFavorite(id)

    fun getAllFavoriteIds(): List<String> = favoritesStore.getAllFavorites().toList()

    private fun posterForEpisode(episodeId: Int): String {
        return when (episodeId) {
            1 -> "https://m.media-amazon.com/images/I/81aA7hEEykL.jpg"
            2 -> "https://m.media-amazon.com/images/I/91Nf0L8QnJL.jpg"
            3 -> "https://m.media-amazon.com/images/I/71MKj4xA7jL.jpg"
            4 -> "https://m.media-amazon.com/images/I/81r+LN6M3TL.jpg"
            5 -> "https://m.media-amazon.com/images/I/71Y7fZrNfNL.jpg"
            6 -> "https://m.media-amazon.com/images/I/81g8vEs4ixL.jpg"
            7 -> "https://m.media-amazon.com/images/I/81Y6f3bW5kL.jpg"
            else -> "https://via.placeholder.com/600x900.png?text=Star+Wars"
        }
    }
}