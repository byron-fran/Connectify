package com.example.connectify.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectify.presentation.screens.contact.AddContactScreen
import com.example.connectify.presentation.screens.contact.ContactDetailScreen
import com.example.connectify.presentation.screens.contact.ContactScreen
import com.example.connectify.presentation.screens.search.SearchScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHostManager() {
    val navController = rememberNavController()

    SharedTransitionLayout {

        NavHost(
            navController = navController,
            startDestination = Screens.Contacts
        ) {

            composable<Screens.Contacts> {
                ContactScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onNavigateTo = {
                        navController.navigate(it)
                    }
                ) {
                    navController.navigate(Screens.ContactDetail(it))
                }
            }
            composable<Screens.ContactDetail> { navBackStackEntry ->

                val contactId = navBackStackEntry.arguments?.getString("contactId")

                ContactDetailScreen(
                    contactId = contactId,
                    sharedTransitionScope   = this@SharedTransitionLayout,
                    animatedContentScope = this@composable

                ) {
                    navController.popBackStack()
                }
            }
            composable<Screens.AddContact> {
                AddContactScreen(
                    onNavigateTo = {
                        navController.navigate(it)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable<Screens.Search> {
                SearchScreen()
            }
        }
    }
}