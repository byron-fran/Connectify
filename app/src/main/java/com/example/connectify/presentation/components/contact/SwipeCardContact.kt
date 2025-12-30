package com.example.connectify.presentation.components.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.utils.ContactCardModifiers

@Composable
fun SwipeCardContact(
    contact: Contact,
    togglingFavoriteId: String?,
    modifier: Modifier = Modifier,
    modifiers: ContactCardModifiers = ContactCardModifiers(),
    onFavoriteToggle: (Contact) -> Unit,
    onChangeModalBottonSheet: () -> Unit,
    onUpdate: (String) -> Unit,
    onRemove: (Contact) -> Unit,
    onNavigate: () -> Unit,
) {


    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onUpdate(contact.id)
                    false
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onRemove(contact)
                    false

                }
                else -> true
            }
        }
    )
    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        if(swipeToDismissBoxState.currentValue != SwipeToDismissBoxValue.Settled) {
            swipeToDismissBoxState.reset()
        }
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = modifier.fillMaxSize(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    Icon(
                        painter = painterResource(R.drawable.icon_edit),
                        contentDescription = "Edit item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.secondary)
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(12.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.error)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
                SwipeToDismissBoxValue.Settled -> {}
            }
        }
    ) {
        ContactCard(
            contact,
            togglingFavoriteId,
            onFavoriteToggle = onFavoriteToggle,
            modifiers = modifiers,
            modifier = modifier,
            onChangeModalBottonSheet = onChangeModalBottonSheet
        ) {
            onNavigate()
        }
    }
}