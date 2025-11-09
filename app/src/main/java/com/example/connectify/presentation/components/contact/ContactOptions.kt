package com.example.connectify.presentation.components.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.connectify.R
import com.example.connectify.presentation.components.global.CustomIconButton
import com.example.connectify.ui.theme.Spacing


@Composable
fun ContactOptions(
    modifier: Modifier = Modifier,
    onClickMessage: () -> Unit,
    onClickPhone: () -> Unit,
    onClickEmail: () -> Unit,
    onClickShare: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_lg)) {

            CustomIconButton(
                icon = R.drawable.icon_message,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
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
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
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
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
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
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .padding(Spacing.spacing_md)
            ) {
                onClickShare()
            }
        }
    }
}