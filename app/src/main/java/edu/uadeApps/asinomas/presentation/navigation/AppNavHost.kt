package edu.uadeApps.asinomas.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.platform.LocalContext
import edu.uadeApps.asinomas.data.repository.ServiceLocator
import edu.uadeApps.asinomas.presentation.detail.FilmDetailScreen
import edu.uadeApps.asinomas.presentation.detail.FilmDetailViewModel
import edu.uadeApps.asinomas.presentation.favorites.FavoritesScreen
import edu.uadeApps.asinomas.presentation.favorites.FavoritesViewModel
import edu.uadeApps.asinomas.presentation.films.FilmsScreen
import edu.uadeApps.asinomas.presentation.films.FilmsViewModel
import edu.uadeApps.asinomas.presentation.resource.ResourceDetailScreen
import edu.uadeApps.asinomas.presentation.resource.ResourceDetailViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository = ServiceLocator.provideRepository(context)

    NavHost(navController = navController, startDestination = Routes.Films.route) {
        composable(Routes.Films.route) {
            val vm: FilmsViewModel = viewModel(factory = FilmsViewModel.Factory(repository))
            FilmsScreen(
                viewModel = vm,
                onFilmClick = { filmUrl ->
                    navController.navigate("detail/${Uri.encode(filmUrl)}")
                },
                onFavoritesClick = {
                    navController.navigate(Routes.Favorites.route)
                }
            )
        }

        composable(Routes.Favorites.route) {
            val vm: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory(repository))
            FavoritesScreen(
                viewModel = vm,
                onItemClick = { url ->
                    if (url.contains("/films/")) {
                        navController.navigate("detail/${Uri.encode(url)}")
                    } else {
                        navController.navigate("resource_detail/${Uri.encode(url)}")
                    }
                }
            )
        }

        composable(
            route = Routes.Detail.route,
            arguments = listOf(navArgument("filmUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val filmUrl = Uri.decode(backStackEntry.arguments?.getString("filmUrl") ?: "")
            val vm: FilmDetailViewModel = viewModel(factory = FilmDetailViewModel.Factory(repository))
            FilmDetailScreen(filmUrl = filmUrl, viewModel = vm)
        }

        composable(
            route = Routes.ResourceDetail.route,
            arguments = listOf(navArgument("resourceUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val resourceUrl = Uri.decode(backStackEntry.arguments?.getString("resourceUrl") ?: "")
            val vm: ResourceDetailViewModel = viewModel(factory = ResourceDetailViewModel.Factory(repository))
            ResourceDetailScreen(resourceUrl = resourceUrl, viewModel = vm)
        }
    }
}