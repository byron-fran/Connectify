package com.example.connectify.presentation.screens.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactList
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.presentation.screens.empty.EmptyScreen
import com.example.connectify.presentation.states.ContactUiEvent
import com.example.connectify.ui.theme.Card
import com.example.connectify.utils.Tag.ADD_CONTACT_BUTTON
import com.example.connectify.utils.Tag.CONTACT_SCREEN
import com.example.connectify.utils.Tag.SETTINGS_BUTTON

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactScreen(
    contactUiState: ContactUIState,
    onEvent: (ContactUiEvent) -> Unit,
    togglingFavoriteId: String?,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onNavigateTo: (Screens) -> Unit,
) {
    val contacts = contactUiState.contacts

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { TitleMedium(stringResource(R.string.all_contacts)) },
                canNavigateBack = false,
                actions = {
                    CustomIconButton(
                        icon = R.drawable.icon_settings,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.testTag(SETTINGS_BUTTON)
                    ) {
                        onNavigateTo(Screens.Settings)
                    }
                }
            ) {
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateTo(Screens.AddContact)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.testTag(ADD_CONTACT_BUTTON)
            ) {  
                CustomIcon(
                    icon = R.drawable.icon_add,
                    modifier = Modifier.size(Card.card_sm),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .testTag(CONTACT_SCREEN)
    ) { paddingValues ->

        if (contacts.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                ContactList(
                    togglingFavoriteId = togglingFavoriteId,
                    contacts = contacts,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    modifier = Modifier,
                    screenKey = "contacts",
                    onChangeFavorite = { contact ->
                        onEvent(ContactUiEvent.ToggleFavorite(contact))
                    },
                    onRemove = { contact ->
                        onEvent(ContactUiEvent.DeleteContact(contact))
                    },
                    onUpdate = { contactId ->
                        onNavigateTo(Screens.EditContact(contactId))
                    }
                ) { contactId ->
                    onNavigateTo(Screens.ContactDetail(contactId, "contacts"))
                }
            }
        } else {
            EmptyScreen(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                BodyLarge(stringResource(R.string.empty_contacts))
            }
        }
    }
}