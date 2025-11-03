package com.example.connectify.presentation.components.global

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.connectify.ui.theme.RoundedCorner

@Composable
fun ButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(
            RoundedCorner.rounded_corner_md
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.5f
            ),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = 0.5f
            )
        ),
        onClick = { onClick() }
    ) {

        BodyMedium(
            text = text,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimary

        )
    }
}