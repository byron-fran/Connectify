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
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connectify.presentation.screens.contact.AddContactScreen
import com.example.connectify.presentation.screens.contact.ContactDetailScreen
import com.example.connectify.presentation.screens.contact.ContactEditScreen
import com.example.connectify.presentation.screens.contact.ContactScreen
import com.example.connectify.presentation.screens.contact.ContactViewModel
import com.example.connectify.presentation.screens.favorites.FavoriteContactsScreen
import com.example.connectify.presentation.screens.search.SearchScreen
import com.example.connectify.presentation.screens.settings.SettingsScreen
import com.example.connectify.presentation.screens.theme.ThemeScreen
import com.example.connectify.presentation.screens.theme.ThemeViewModel

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
                val contactViewModel : ContactViewModel = hiltViewModel()
                ContactScreen(
                    contactUiState = contactViewModel.contactUiState.collectAsState().value,
                    onEvent = contactViewModel::onEvent,
                    togglingFavoriteId = contactViewModel.togglingFavoriteId.value,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                ) { route ->
                    navHostController.navigate(route)
                }
            }
            composable<Screens.ContactDetail> { navBackStackEntry ->

                val contactId = navBackStackEntry.arguments?.getString("contactId")
                val screenKey = navBackStackEntry.arguments?.getString("screenKey")
                val contactViewModel : ContactViewModel = hiltViewModel()
                val togglingFavoriteId = contactViewModel.togglingFavoriteId.value

                ContactDetailScreen(
                    contactId = contactId,
                    screenKey = screenKey,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    togglingFavoriteId = togglingFavoriteId,
                    contactUiState = contactViewModel.contactUiState.collectAsState().value,
                    onEvent = contactViewModel::onEvent,
                    onNavigateToEdit = {
                        navHostController.navigate(Screens.EditContact(contactId))
                    }
                ) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.AddContact> {
                val contactViewModel : ContactViewModel = hiltViewModel()
                AddContactScreen(
                    onEvent = { contactUiEvent ->
                        contactViewModel.onEvent(contactUiEvent)
                    }
                ) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.EditContact> { navBackStackEntry ->
                val contactId = navBackStackEntry.arguments?.getString("contactId")
                val contactViewModel : ContactViewModel = hiltViewModel()
                ContactEditScreen(
                    contactId = contactId,
                    contactUiState = contactViewModel.contactUiState.collectAsState().value,
                    onEvent = contactViewModel::onEvent
                ) {
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
                val contactViewModel = hiltViewModel<ContactViewModel>()
                FavoriteContactsScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    contactUiState = contactViewModel.contactUiState.collectAsState().value,
                    onEvent = contactViewModel::onEvent,
                    togglingFavoriteId = contactViewModel.togglingFavoriteId.value,
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    onNavigateTo = { route ->
                        navHostController.navigate(route)
                    }
                )
            }
            composable<Screens.Settings> {
                val contactViewModel = hiltViewModel<ContactViewModel>()
                SettingsScreen(
                    isNotEmptyContacts = contactViewModel.contactUiState.collectAsState().value.contacts.isNotEmpty(),
                    onEvent = contactViewModel::onEvent,
                    onNavigateTo = { route ->
                        navHostController.navigate(route)
                    }
                ) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.Theme> {
                val themeViewModel = hiltViewModel<ThemeViewModel>()
                ThemeScreen(
                    themeMode = themeViewModel.themeMode.collectAsState().value,
                    onChangeTheme = themeViewModel::setThemeMode,
                ) {
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