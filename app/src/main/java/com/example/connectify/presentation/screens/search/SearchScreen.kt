package com.example.connectify.presentation.screens.search

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.R
import com.example.connectify.presentation.components.contact.ContactList
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.search.ConnectifySearchBar
import com.example.connectify.presentation.navigation.Screens
import com.example.connectify.presentation.screens.contact.ContactViewModel
import com.example.connectify.ui.theme.Spacing

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigationTo: (Screens) -> Unit
) {

    val searchUiState = searchViewModel.searchUiState.collectAsState()
    val togglingFavoriteId = contactViewModel.togglingFavoriteId.value
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(query) {
        if (query.trim().length > 1) {
            searchViewModel.searchContacts(query)
            expanded = true
        } else {
            expanded = false
            searchViewModel.clearState()
        }
    }

    Column(modifier = Modifier.statusBarsPadding()) {

        ConnectifySearchBar(
            query = query,
            expanded = expanded,
            onQueryChange = { query = it },
            onClearQuery = { query = "" },
            onSearch = {},
            onExpandedChange = {}
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(Spacing.spacing_lg))
                if (searchUiState.value.result.isNotEmpty()) {
                    ContactList(
                        togglingFavoriteId = togglingFavoriteId,
                        contacts = searchUiState.value.result,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope,
                        screenKey = "search",
                        onChangeFavorite = { contact ->
                            contactViewModel.updateContact(
                                contact.copy(
                                    isFavorite = !contact.isFavorite
                                )
                            )
                        },
                        onRemove = { contact ->
                            contactViewModel.deleteContact(contact)
                        },
                        onUpdate = { contactId ->
                            onNavigationTo(Screens.EditContact(contactId))
                        }
                    ) {
                        onNavigationTo(Screens.ContactDetail(it, "search"))
                    }
                } else {
                    Box(contentAlignment = Alignment.Center) {
                        BodyLarge(stringResource(id = R.string.no_results))
                    }
                }
            }
        }
    }
}