package com.example.connectify.presentation.components.contact

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.connectify.R
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.global.BodyLarge
import com.example.connectify.presentation.components.global.BodySmall
import com.example.connectify.presentation.components.global.BoxCircle
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.presentation.components.global.LazyImage
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.Spacing
import com.example.connectify.utils.ContactCardModifiers

@Composable
fun ContactCard(
    contact: Contact,
    modifiers: ContactCardModifiers = ContactCardModifiers(),
    onNavigate: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().animateContentSize(),
        shape = RoundedCornerShape(Spacing.spacing_md),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { isExpanded = !isExpanded }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_md),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(vertical = Spacing.spacing_sm)
        ) {
            LazyImage(
                uri = contact.imageUrl,
                modifier = modifiers.imageModifier.clickable { onNavigate() }.clip(CircleShape).size(Card.card_md)
            ) {
                BoxCircle(
                    modifier = modifiers.imageModifier
                        .background(
                            shape = CircleShape,
                            color = contact.colorDefault
                        )
                        .size(Card.card_md)
                        .clickable { onNavigate() },
                    content = {
                        BodyLarge(
                            text = contact.name.first().toString().uppercase(),
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.spacing_xs)
            ) {
                BodyLarge(
                    text = contact.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = modifiers.textModifier

                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_sm)
                    ) {
                        BodySmall(
                            text = contact.phoneNumber.toString(),
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.7f
                            )
                        )
                        contact.email?.let {
                            CustomIcon(
                                icon = R.drawable.icon_circle,
                                size = 4.dp,
                                color = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.7f
                                )
                            )
                            BodySmall(
                                text = contact.email,
                                color = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.7f
                                )
                            )
                        }
                    }
                }
            }
        }

        if (isExpanded) {
            ContactCardBottom(
                onClickMessage = { /*TODO*/ },
                onClickPhone = { /*TODO*/ },
                onClickEmail = { /*TODO*/ },
                onClickShare = { /*TODO*/ }

            )
        }
    }

}

@Composable
fun ContactCardBottom(
    modifier: Modifier = Modifier,
    onClickMessage: () -> Unit,
    onClickPhone: () -> Unit,
    onClickEmail: () -> Unit,
    onClickShare: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.spacing_md),
        horizontalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_lg)
        ) {

            CustomIconButton(
                icon = R.drawable.icon_message,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        shape = CircleShape
                    )
                    .padding(Spacing.spacing_md)
            ) {
                onClickMessage()
            }
            CustomIconButton(
                icon = R.drawable.icon_phone,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        shape = CircleShape
                    )
                    .padding(Spacing.spacing_md)
            ) {
                onClickPhone()
            }
            CustomIconButton(
                icon = R.drawable.icon_email,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        shape = CircleShape
                    )
                    .padding(Spacing.spacing_md)
            ) {
                onClickEmail()
            }
            CustomIconButton(
                icon = R.drawable.icon_share,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.2f
                        ),
                        shape = CircleShape
                    )
                    .padding(Spacing.spacing_md)
            ) {
                onClickShare()
            }
        }
    }
}