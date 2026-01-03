package com.example.connectify.presentation.screens.favorites

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactList
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.presentation.screens.contact.ContactViewModel
import com.example.connectify.presentation.screens.empty.EmptyScreen
import com.example.connectify.ui.theme.Spacing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteContactsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigateBack: () -> Unit,
    onNavigateTo: (Screens) -> Unit
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

        val favorites = favoritesContact.filter { it.isFavorite }
        if (favorites.isNotEmpty()){
            Column(modifier = Modifier.padding(paddingValues)) {
                ContactList(
                    togglingFavoriteId = togglingFavoriteId,
                    contacts = favorites,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    modifier = Modifier.padding(horizontal = Spacing.spacing_sm),
                    screenKey = "favorites",
                    onChangeFavorite = { contact ->
                        contactViewModel.updateContact(contact.copy( isFavorite = !contact.isFavorite))
                    },
                    onRemove = { contact ->
                        contactViewModel.deleteContact(contact)
                    },
                    onUpdate = { contactId ->
                        onNavigateTo(Screens.EditContact(contactId))
                    }
                ) {
                    onNavigateTo(Screens.ContactDetail(it, "favorites"))
                }
            }
        }else {
            EmptyScreen(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                BodyLarge(stringResource(R.string.empty_favorites))
            }
        }
    }
}