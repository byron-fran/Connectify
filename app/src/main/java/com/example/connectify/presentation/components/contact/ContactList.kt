package com.example.connectify.presentation.components.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connectify.domain.models.Contact
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ContactCardModifiers
import com.example.connectify.utils.SharedTransition

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactList(
    contacts: List<Contact>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
    enableSharedTransitions: Boolean = true,
    screenKey: String? = null,
    onChangeFavorite: (Contact) -> Unit,
    onNavigateToContactDetail: (String) -> Unit
) {
    var selectedContactForModal by remember { mutableStateOf<Contact?>(null) }

    with(sharedTransitionScope) {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md)
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
                ContactCard(
                    contact = contact,
                    modifiers = contactModifier,
                    onChangeFavorite = { onChangeFavorite(contact) },
                    onChangeModalBottonSheet = {
                        selectedContactForModal = contact
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactModalBottomSheet(
    contact: Contact,
    isActiveModalSheet: Boolean,
    onChangeContactModalSheet: () -> Unit,
) {
    if (isActiveModalSheet) {
        ModalBottomSheet(
            onDismissRequest = onChangeContactModalSheet,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier.height(250.dp),
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing_md),

            ) {
                CallPhone(contact.phoneNumber.toString())
                SendMessage(contact.phoneNumber.toString())
                contact.email?.let { e ->
                    SendEmail(e)
                }
            }
        }
    }
}