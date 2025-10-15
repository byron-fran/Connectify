package com.example.connectify.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectify.presentation.screens.contact.ContactDetailScreen
import com.example.connectify.presentation.screens.contact.ContactScreen

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
                    animatedContentScope = this@composable
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
        }
    }
}