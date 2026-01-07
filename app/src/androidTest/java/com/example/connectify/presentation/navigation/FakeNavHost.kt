package com.example.connectify.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.screens.contact.AddContactScreen
import com.example.connectify.presentation.screens.contact.ContactDetailScreen
import com.example.connectify.presentation.screens.contact.ContactEditScreen
import com.example.connectify.presentation.screens.contact.ContactScreen
import com.example.connectify.presentation.screens.contact.ContactUIState
import com.example.connectify.presentation.screens.favorites.FavoriteContactsScreen
import com.example.connectify.presentation.screens.search.SearchScreen
import com.example.connectify.presentation.screens.settings.SettingsScreen
import com.example.connectify.presentation.screens.theme.ThemeScreen
import com.example.connectify.utils.ThemeMode

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FakeNavHost(
    navHostController: TestNavHostController,
    startDestination: Any = Screens.Contacts
) {
    val contact =
        Contact(id = "1", name = "jose", email = "1byron1999@gmail.com", phoneNumber = 123456789)
    val contactUiState = ContactUIState(contacts = listOf(contact))

    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = startDestination
        ) {
            composable<Screens.Contacts> {
                ContactScreen(
                    contactUiState = contactUiState,
                    onEvent = {},
                    togglingFavoriteId = null,
                    onNavigateTo = {
                        navHostController.navigate(it)
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
            composable<Screens.ContactDetail> {
                ContactDetailScreen(
                    contactId = "1",
                    screenKey = "contacts",
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    togglingFavoriteId = null,
                    contactUiState = ContactUIState(contact = contact),
                    onEvent = {},
                    onNavigateToEdit = {
                        navHostController.navigate(Screens.EditContact("1"))
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<Screens.AddContact> {
                AddContactScreen(onEvent = {}) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.EditContact> {
                ContactEditScreen(
                    contactId = "1",
                    contactUiState = ContactUIState(contact = contact),
                    onEvent = {},
                ) {
                    navHostController.popBackStack()
                }
            }
            composable<Screens.Search> {
                SearchScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                ) { route ->
                    navHostController.navigate(route)
                }
            }
            composable<Screens.Favorites> {
                FavoriteContactsScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    contactUiState = contactUiState,
                    onEvent = {},
                    togglingFavoriteId = null,
                    onNavigateBack = {
                        navHostController.popBackStack()
                    },
                    onNavigateTo = { route ->
                        navHostController.navigate(route)
                    }
                )
            }
            composable<Screens.Settings> {
                SettingsScreen(
                    isNotEmptyContacts = true,
                    onEvent = {},
                    onNavigateTo = { route ->
                        navHostController.navigate(route)
                    },
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<Screens.Theme> {
                ThemeScreen(
                    themeMode = ThemeMode.FOLLOW_SYSTEM,
                    onChangeTheme = {},
                    onNavigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }
}