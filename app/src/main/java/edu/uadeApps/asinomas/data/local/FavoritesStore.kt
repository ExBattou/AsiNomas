package edu.uadeApps.asinomas.data.local

import android.content.Context

class FavoritesStore(context: Context) {
    private val prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    fun isFavorite(id: String): Boolean {
        return prefs.getStringSet(KEY_FAVORITES, emptySet())?.contains(id) == true
    }

    fun toggleFavorite(id: String) {
        val current = prefs.getStringSet(KEY_FAVORITES, emptySet())?.toMutableSet() ?: mutableSetOf()
        if (current.contains(id)) current.remove(id) else current.add(id)
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
    }

    fun getAllFavorites(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    companion object {
        private const val KEY_FAVORITES = "favorites"
    }
}