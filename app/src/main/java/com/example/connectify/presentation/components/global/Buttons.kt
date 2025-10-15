package com.example.connectify.presentation.components.global

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
        modifier = modifier.background(MaterialTheme.colorScheme.primary),
        enabled = enabled,
        shape = RoundedCornerShape(
            RoundedCorner.rounded_corner_md
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