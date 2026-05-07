package edu.uadeApps.asinomas.presentation.navigation

sealed class Routes(val route: String) {
    data object Films : Routes("films")
    data object Favorites : Routes("favorites")
    data object Detail : Routes("detail/{filmUrl}") {
        fun create(filmUrl: String): String = "detail/$filmUrl"
    }
}