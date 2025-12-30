package com.example.connectify.presentation.screens.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactList
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.Spacing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel(),
    onNavigateTo: (Screens) -> Unit,
) {
    val contacts = contactViewModel.contactState.collectAsState().value.contacts
    val togglingFavoriteId = contactViewModel.togglingFavoriteId.value

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = { TitleMedium(stringResource(R.string.all_contacts)) },
                canNavigateBack = false,
                actions = {
                    CustomIconButton(
                        icon = R.drawable.icon_settings,
                        color = MaterialTheme.colorScheme.onBackground
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
            ) {
                CustomIcon(
                    icon = R.drawable.icon_add,
                    modifier = Modifier.size(Card.card_sm),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.semantics{
            contentDescription = "Contact Screen"
        }
    ) { paddingValues ->
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
                    contactViewModel.toggleFavorite(contact)
                },
                onRemove = {contact ->
                    contactViewModel.deleteContact(contact)
                },
                onUpdate = { contactId ->
                    onNavigateTo(Screens.EditContact(contactId))
                }
            ) { contactId ->
                onNavigateTo(Screens.ContactDetail(contactId, "contacts"))
            }
        }
    }
}