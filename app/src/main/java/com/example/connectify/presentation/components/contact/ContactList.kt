package com.example.connectify.presentation.components.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.screens.contact.ContactDeleteDialog
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ContactCardModifiers
import com.example.connectify.utils.SharedTransition

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactList(
    togglingFavoriteId: String?,
    contacts: List<Contact>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    enableSharedTransitions: Boolean = true,
    screenKey: String? = null,
    onChangeFavorite: (Contact) -> Unit,
    onRemove: (Contact) -> Unit,
    onUpdate: (String) -> Unit,
    onNavigateToContactDetail: (String) -> Unit
) {
    var selectedContactForModal by remember { mutableStateOf<Contact?>(null) }
    var selectedContactForDialog by remember  { mutableStateOf<Contact?>(null)}
    var showDialog by remember { mutableStateOf(false) }

    with(sharedTransitionScope) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(350.dp),
            contentPadding = PaddingValues(horizontal = Spacing.spacing_sm),
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
            modifier = modifier
        ) {
            items(contacts) { contact ->
                val contactModifier = if (enableSharedTransitions) {
                    ContactCardModifiers(
                        imageModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionImageKey(
                                    contact.id,
                                    screenKey
                                )
                            ),
                        ),
                        textModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionTitleKey(
                                    contact.id,
                                    screenKey
                                )
                            ),
                            boundsTransform = { initialBounds, targetBounds ->
                                keyframes {
                                    durationMillis = 800
                                    initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
                                    targetBounds at 800
                                }
                            }
                        )
                    )
                } else {
                    ContactCardModifiers()
                }

                SwipeCardContact(
                    togglingFavoriteId = togglingFavoriteId,
                    contact = contact,
                    modifiers = contactModifier,
                    onFavoriteToggle = { onChangeFavorite(it) },
                    onChangeModalBottonSheet = {
                        selectedContactForModal = contact
                    },
                    onUpdate = { contactId ->
                        onUpdate(contactId)
                    },
                    onRemove = { c ->
                        showDialog = true
                        selectedContactForDialog = c
                    }

                ) {
                    onNavigateToContactDetail(contact.id)
                }
            }


        }
    }

    selectedContactForModal?.let { contact ->
        ContactModalBottomSheet(
            contact = contact,
            isActiveModalSheet = true,
            onChangeContactModalSheet = {
                selectedContactForModal = null
            }
        )
    }

    selectedContactForDialog?.let { c ->
        ContactDeleteDialog(
            text = stringResource(R.string.confirm_delete_message),
            showDialog,
            onConfirm = {
            onRemove(c)
            showDialog = false
            selectedContactForDialog = null
        }) {
            showDialog = false
            selectedContactForDialog = null
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactModalBottomSheet(
    contact: Contact,
    isActiveModalSheet: Boolean,
    modifier : Modifier = Modifier,
    onChangeContactModalSheet: () -> Unit,
) {
    if (isActiveModalSheet) {
        ModalBottomSheet(
            onDismissRequest = onChangeContactModalSheet,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = modifier.height(250.dp),
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
                ) {
                CallPhone(
                    phoneNumber = contact.phoneNumber.toString()){
                    onChangeContactModalSheet()
                }
                SendMessage(
                    phoneNumber=contact.phoneNumber.toString()){
                    onChangeContactModalSheet()
                }
                contact.email?.let { e ->
                    if(e.trim().isNotEmpty()){
                        SendEmail(e){
                            onChangeContactModalSheet()
                        }
                    }
                }
            }
        }
    }
}