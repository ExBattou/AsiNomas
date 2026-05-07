package edu.uadeApps.asinomas.data.network


import edu.uadeApps.asinomas.data.model.FilmDto
import edu.uadeApps.asinomas.data.model.ResourceDto
import retrofit2.http.GET
import retrofit2.http.Url

interface SwapiApi {
    @GET("films")
    suspend fun getFilms(): List<FilmDto>

    @GET
    suspend fun getResource(@Url url: String): ResourceDto
}