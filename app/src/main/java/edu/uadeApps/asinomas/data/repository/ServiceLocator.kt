package edu.uadeApps.asinomas.data.repository

import android.content.Context
import edu.uadeApps.asinomas.data.local.FavoritesStore
import edu.uadeApps.asinomas.data.network.RetrofitClient

object ServiceLocator {
    @Volatile
    private var repository: FilmsRepository? = null

    fun provideRepository(context: Context): FilmsRepository {
        return repository ?: synchronized(this) {
            repository ?: FilmsRepository(
                api = RetrofitClient.api,
                favoritesStore = FavoritesStore(context.applicationContext)
            ).also { repository = it }
        }
    }
}