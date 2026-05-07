# StarWarsFilms (Android - Kotlin + Compose)

Arquitectura implementada:
- MVVM
- Repository
- Retrofit (SWAPI)
- SharedPreferences para favoritos

## Pantallas
1. Lista de filmes estilo carrusel horizontal (tipo Netflix).
2. Detalle del film con director, producer, release_date, created, edited, url y listas:
   - characters
   - planets
   - starships
   - vehicles
   - species

Cada elemento (pelicula o recurso relacionado) puede marcarse como favorito.

## Endpoint base
- https://swapi.info/api/films

## Notas
- Las portadas se mapean por `episode_id`.
- Los nombres de characters/planets/starships/vehicles/species se resuelven con llamadas adicionales usando las URLs del film.

## Abrir en Android Studio
1. Open -> seleccionar esta carpeta.
2. Sync Gradle.
3. Ejecutar en emulador/dispositivo.
