package com.example.connectify.presentation.screens.favorites

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactList
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.presentation.screens.contact.ContactViewModel
import com.example.connectify.ui.theme.Spacing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteContactsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Screens) -> Unit
) {

    val favoritesContact = contactViewModel.contactState.collectAsState().value.contacts
    val togglingFavoriteId = contactViewModel.togglingFavoriteId.value
    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = {
                    TitleMedium(stringResource(R.string.favorites))
                }
            ) {
                onNavigateBack()
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ContactList(
                togglingFavoriteId = togglingFavoriteId,
                contacts = favoritesContact.filter { it.isFavorite },
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                modifier = Modifier.padding(horizontal = Spacing.spacing_sm),
                enableSharedTransitions = true,
                screenKey = "favorites",
                onChangeFavorite = { contact ->
                    contactViewModel.updateContact(
                        contact.copy(
                            isFavorite = !contact.isFavorite
                        )
                    )
                }
            ) {
                onNavigateToDetail(Screens.ContactDetail(it, "favorites"))
            }
        }
    }
}