package com.example.connectify.presentation.components.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.connectify.domain.models.Contact
import com.example.connectify.presentation.components.global.BoxCircle
import com.example.connectify.presentation.components.global.LazyImage
import com.example.connectify.presentation.components.global.TitleLarge
import com.example.connectify.presentation.components.global.TitleMedium
import com.example.connectify.ui.theme.Card
import com.example.connectify.ui.theme.Spacing

@Composable
fun ContactHeader(
    modifier: Modifier = Modifier,
    contact: Contact
) {

    Box(
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.spacing_lg)
        ) {

            LazyImage(
                uri = contact.imageUrl,
                modifier = Modifier
                    .size(Card.card_3xl)
                    .clip(CircleShape)
            ) {
                BoxCircle(
                    modifier = Modifier
                        .size(Card.card_3xl)
                        .background(
                            shape = CircleShape,
                            color = contact.colorDefault.copy(alpha = 0.6f)
                        ),
                    content = {
                        TitleLarge(
                            text = contact.name.first().toString().uppercase(),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
            TitleMedium(text = contact.name)
        }
    }
}
