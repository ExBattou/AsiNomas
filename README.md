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

## Explicacion Tecnica

**1) Definiciones base**

- `MVVM` (Model-View-ViewModel):
1. `Model`: datos y lógica de negocio (API, repositorio, entidades).
2. `View`: UI (Compose), solo muestra estado y dispara eventos.
3. `ViewModel`: conecta View con Model, maneja estado y casos de uso.

- `Repository Pattern`:
1. Es una capa intermedia entre UI/ViewModel y fuentes de datos.
2. Oculta de dónde vienen los datos (red, cache, local).
3. Entrega una API limpia al ViewModel (`getFilms`, `toggleFavorite`, etc.).

- `SharedPreferences`:
1. Almacenamiento local clave-valor persistente.
2. Se usa para datos simples (booleans, strings, sets).
3. En esta app guarda un `Set<String>` con URLs favoritas.

---

**2) Estructura por capas en tu proyecto**

- `data/model`: DTOs de red
  - [FilmDto.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/model/FilmDto.kt)
  - [ResourceDto.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/model/ResourceDto.kt)

- `data/network`: Retrofit
  - [SwapiApi.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/network/SwapiApi.kt)
  - [RetrofitClient.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/network/RetrofitClient.kt)

- `data/local`: persistencia local
  - [FavoritesStore.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/local/FavoritesStore.kt)

- `data/repository`: orquestación de datos
  - [FilmsRepository.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/repository/FilmsRepository.kt)
  - [ServiceLocator.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/repository/ServiceLocator.kt)

- `domain`: modelos usados por UI
  - [Models.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/domain/Models.kt)

- `presentation`: View + ViewModel
  - films/detail/favorites/resource + navigation

---

**3) Cómo funciona el Repository en esta app**

En [FilmsRepository.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/repository/FilmsRepository.kt):

1. `getFilms()`
- Llama a `api.getFilms()` (Retrofit).
- Ordena por `episodeId`.
- Mapea `FilmDto` -> `Film` (modelo de dominio).
- Agrega `posterUrl`.
- Guarda en `filmsCache` (cache en memoria para no repetir llamadas).

2. `getFilmByUrl(url)`
- Busca una película puntual dentro de la lista ya cargada.

3. `resolveResources(urls)`
- Para cada URL (character, planet, etc.) hace `api.getResource(url)`.
- Devuelve `ResourceItem(label, url)` listo para UI.

4. Favoritos delegados al almacenamiento local:
- `isFavorite(id)`
- `toggleFavorite(id)`
- `getAllFavoriteIds()`

5. `getResourceByUrl(url)`
- Trae detalle de recurso individual para la pantalla genérica.

Idea clave para explicar: el `ViewModel` nunca habla directo con Retrofit ni con SharedPreferences; siempre pasa por Repository.

---

**4) Cómo funciona SharedPreferences en esta app**

En [FavoritesStore.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/local/FavoritesStore.kt):

1. Se crea con:
- `context.getSharedPreferences("favorites_prefs", MODE_PRIVATE)`

2. Estructura guardada:
- clave: `favorites`
- valor: `Set<String>` de IDs, donde el ID es la URL del recurso o película.

3. Operaciones:
- `isFavorite(id)`: consulta si la URL está en el set.
- `toggleFavorite(id)`: si existe la quita, si no existe la agrega.
- `getAllFavorites()`: devuelve todo el set para pantalla de favoritos.

Ventaja de usar URL como ID:
- Es única globalmente en SWAPI.
- Sirve para películas y recursos con el mismo mecanismo.

---

**5) Flujo completo de datos (end-to-end)**

1. `View` (Compose) llama acción del `ViewModel`.
2. `ViewModel` ejecuta corrutina (`viewModelScope.launch`).
3. `ViewModel` llama método de `Repository`.
4. `Repository` decide origen:
- red (Retrofit),
- cache en memoria (`filmsCache`),
- local (`SharedPreferences` via `FavoritesStore`).
5. `Repository` devuelve modelos listos para UI.
6. `ViewModel` actualiza `StateFlow`.
7. `View` observa `collectAsState()` y recompone.

---

**6) Rol de ServiceLocator**

En [ServiceLocator.kt](/Users/adriannarducci/Documents/Codex/2026-05-07/hey-codex-como-estas-necesito-que/app/src/main/java/com/example/starwarsfilms/data/repository/ServiceLocator.kt):

- Crea una sola instancia de `FilmsRepository` (singleton simple).
- Inyecta dependencias (`SwapiApi` y `FavoritesStore`).
- Evita recrear repositorio por pantalla.

En clase podés decir: es una forma liviana de DI manual, alternativa a Hilt para proyectos chicos.

---

**7) Puntos fuertes para defender en clase**

1. Separación de responsabilidades clara.
2. UI desacoplada de red y persistencia.
3. Favoritos persistentes entre cierres de app.
4. Reutilización: mismo favorito para film y recursos.
5. Escalable: podés reemplazar SharedPreferences por Room sin tocar la UI.
