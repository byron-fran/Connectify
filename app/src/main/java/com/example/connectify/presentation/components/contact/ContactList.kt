package com.example.connectify.presentation.components.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                ) {
                    onNavigateToContactDetail(contact.id)
                }
            }
        }
    }
}