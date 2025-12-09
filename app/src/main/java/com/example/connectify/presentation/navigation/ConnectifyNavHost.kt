package com.example.connectify.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connectify.presentation.screens.settings.SettingsScreen
import com.example.connectify.presentation.screens.contact.AddContactScreen
import com.example.connectify.presentation.screens.contact.ContactDetailScreen
import com.example.connectify.presentation.screens.contact.ContactEditScreen
import com.example.connectify.presentation.screens.contact.ContactScreen
import com.example.connectify.presentation.screens.favorites.FavoriteContactsScreen
import com.example.connectify.presentation.screens.search.SearchScreen
import com.example.connectify.presentation.screens.theme.ThemeScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ConnectifyNavHost(
    navHostController: NavHostController,
) {

    SharedTransitionLayout {

        NavHost(
            navController = navHostController,
            startDestination = Screens.Contacts
        ) {

            composable<Screens.Contacts> {
                ContactScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                ) { route ->
                    navHostController.navigate(route)
                }
            }
            composable<Screens.ContactDetail> { navBackStackEntry ->

                val contactId = navBackStackEntry.arguments?.getString("contactId")
                val screenKey = navBackStackEntry.arguments?.getString("screenKey")

                ContactDetailScreen(
                    contactId = contactId,
                    screenKey = screenKey,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onNavigateToEdit = {
                        navHostController.navigate(Screens.EditContact(contactId))
                    }

                ) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.AddContact> {
                AddContactScreen(
                    onNavigateTo = { route ->
                        navHostController.navigate(route)
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<Screens.EditContact> { navBackStackEntry ->
                val contactId = navBackStackEntry.arguments?.getString("contactId")
                ContactEditScreen(contactId) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.Search>(
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }
            ) {
                SearchScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                ) { route ->
                    navHostController.navigate(route)
                }
            }
            composable<Screens.Favorites>(
                enterTransition = { enterAnimation() },
                exitTransition = { exitAnimation() }
            ) {
                FavoriteContactsScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    onNavigateToDetail = { route ->
                        navHostController.navigate(route)
                    }
                )
            }
            composable<Screens.Settings> {
                SettingsScreen(
                    onNavigateTo = { route ->
                        navHostController.navigate(route)
                    }
                ) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.Theme> {
                ThemeScreen() {
                    navHostController.popBackStack()
                }
            }
        }
    }
}

fun NavController.navigateSingleBottomTo(screen: String) {

    this.navigate(screen) {
        popUpTo(graph.findStartDestination().id) {
            saveState = false
        }
        launchSingleTop = true
        restoreState = true
    }
}


fun enterAnimation(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { w -> -w },
        animationSpec = tween(delayMillis = 300)
    ) + fadeIn()
}

fun exitAnimation(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { w -> w },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy
        )
    ) + fadeOut()
}