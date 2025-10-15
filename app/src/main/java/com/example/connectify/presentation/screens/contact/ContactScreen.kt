package com.example.connectify.presentation.screens.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactList
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.Spacing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel(),
    onNavigateToContactDetail: (String) -> Unit

) {
    val contacts = contactViewModel.contactState.collectAsState().value.contacts

    Scaffold(
        topBar = {
            ConnectifyToAppBar(
                title = stringResource(R.string.all_contacts),
                canNavigateBack = false,
            ) {}
        },
        bottomBar = {
            // TODO: Add bottom bar
        },
        floatingActionButton = {
            FloatingActionButton(

                onClick = {
                    // TODO: Navigate to add contact screen
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                CustomIcon(
                    icon = R.drawable.icon_add,
                    size = Card.card_sm,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            ContactList(
                contacts = contacts,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                modifier = Modifier.padding(horizontal = Spacing.spacing_sm)
            ) {
                onNavigateToContactDetail(it)
            }
        }
    }
}