package com.example.connectify.presentation.screens.contact

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.connectify.presentation.components.contact.ContactHeader
import com.example.connectify.presentation.components.global.ConnectifyToAppBar
import com.example.connectify.utils.ContactCardModifiers
import com.example.connectify.utils.SharedTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactDetailScreen(
    contactId: String?,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    contactViewModel: ContactViewModel = hiltViewModel<ContactViewModel>(),
    onNavigate: () -> Unit

) {
    val scrollState = rememberScrollState()
    val contact = contactViewModel.contactState.collectAsState().value.contact

    LaunchedEffect(contactId) {
        contactId?.let {
            withContext(Dispatchers.IO) {
                contactViewModel.getContactById(contactId)
            }
        }
    }

    Scaffold(
        topBar = { ConnectifyToAppBar(contact?.name ?: "") { onNavigate() } },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
    ) { paddingValues ->
        contact?.let {
            with(sharedTransitionScope) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                ) {
                    val contactModifier = ContactCardModifiers(

                        imageModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionImageKey(contact.id)
                            ),
                        ),
                        textModifier = Modifier.sharedElement(
                            animatedVisibilityScope = animatedContentScope,
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = SharedTransition.sharedTransitionTitleKey(contact.id)
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
                    ContactHeader(
                        contact = it,
                        modifiers = contactModifier
                    )
                }
            }
        }
    }
}
