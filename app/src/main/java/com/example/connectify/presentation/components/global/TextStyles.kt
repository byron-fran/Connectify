package com.example.connectify.presentation.components.global

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.connectify.ui.theme.Typography

@Composable
fun TitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground
) {

    Text(
        modifier = modifier,
        text = text,
        style = Typography.titleLarge,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun TitleMedium(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = Typography.titleMedium,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
fun BodyLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground
) {

    Text(
        text = text,
        style = Typography.bodyLarge,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = modifier
    )
}

@Composable
fun BodyMedium(
    text: String,
    modifier : Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1
){
    Text(
        text = text,
        style = Typography.bodyMedium,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Composable
fun BodySmall(
    text: String,
    modifier : Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1
){
    Text(
        text = text,
        style = Typography.bodySmall,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Composable
fun LabelSmall(
    text: String,
    modifier : Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1
) {
    Text(
        text = text,
        style = Typography.labelSmall,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier
    )
}


@Composable
fun LabelMedium(
    text: String,
    modifier : Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1
){
    Text(
        text = text,
        style = Typography.labelMedium,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Composable
fun LabelLarge(
    text: String,
    modifier : Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1
){
    Text(
        text = text,
        style = Typography.labelLarge,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        modifier = modifier
    )
}