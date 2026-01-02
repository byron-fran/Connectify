package com.example.connectify.presentation.components.contact

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.connectify.R
import com.example.connectify.presentation.components.global.BodyMedium
import com.example.connectify.presentation.components.global.CustomIcon
import com.example.connectify.ui.theme.RoundedCorner
import com.example.connectify.ui.theme.Spacing
import androidx.core.net.toUri

@Composable
fun ContactCardAction(
    title: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(RoundedCorner.rounded_corner_md),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = Spacing.spacing_lg,
                horizontal = Spacing.spacing_sm
            ),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing_lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(icon)
            BodyMedium(text = title, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SendMessage(
    phoneNumber: String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {}
) {

    val context = LocalContext.current

    ContactCardAction(
        title = stringResource(R.string.send_message),
        icon = R.drawable.icon_message,
        modifier = modifier
    ) {
        sendSMS(phoneNumber, context)
        onClick()
    }
}

@Composable
fun SendEmail(
    to: String,
    modifier: Modifier = Modifier,
    onClick :() -> Unit = {}
) {

    val context = LocalContext.current

    ContactCardAction(
        title = stringResource(R.string.send_email),
        icon = R.drawable.icon_email,
        modifier = modifier
    ) {
        sendEmail(to, context)
        onClick()
    }
}

@Composable
fun CallPhone(
    phoneNumber: String,
    modifier: Modifier = Modifier,
    onClick :() -> Unit = {}
) {

    val context = LocalContext.current

    ContactCardAction(
        title = stringResource(R.string.call_contact),
        icon = R.drawable.icon_phone,
        modifier = modifier
    ) {
        callPhone(phoneNumber, context)
        onClick()
    }
}

fun sendEmail(to: String, context : Context) {

    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
    intent.putExtra(Intent.EXTRA_SUBJECT, "")
    intent.putExtra(Intent.EXTRA_TEXT, "")

    context.startActivity(Intent.createChooser(intent, context.getString(R.string.send_email)))
}

fun sendSMS(phoneNumber: String, context: Context) {

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("smsto:$phoneNumber")
        putExtra("sms_body", "")
    }
    context.startActivity(intent)

}

fun callPhone(phoneNumber : String, context : Context) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:$phoneNumber".toUri()
    }

    context.startActivity(intent)
}