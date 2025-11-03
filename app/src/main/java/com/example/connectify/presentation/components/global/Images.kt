package com.example.connectify.presentation.components.global

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.connectify.utils.BitMapUtils
import com.example.connectify.utils.ByteArrayUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun LazyImage(
    uri: String?,
    modifier: Modifier = Modifier,
    defaultContent: @Composable () -> Unit
) {

    var imageBitMap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(uri) {
        if(uri?.isNotBlank() == true) {
            withContext(Dispatchers.IO) {
                uri.let {
                    val byteArray = ByteArrayUtils.getByteArray(uri, context)
                    byteArray?.let {
                        val bitmap = BitMapUtils.decodeBitmapWithSubsampling(byteArray, 300, 300)
                        imageBitMap = bitmap
                    }
                }
            }
        }
    }

    imageBitMap?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentScale = ContentScale.Crop,
            modifier = modifier,
            contentDescription = null
        )
    } ?: defaultContent()
}

@Composable
fun CustomImage(uri: String, modifier: Modifier = Modifier) {
    var imageBitMap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(uri) {
        if (uri.isNotBlank()) {
            withContext(Dispatchers.IO) {
                val byteArray = ByteArrayUtils.getByteArray(uri, context)
                byteArray?.let {
                    val bitmap = BitMapUtils.decodeBitmapWithSubsampling(byteArray, 300, 300)
                    imageBitMap = bitmap
                }
            }
        }
    }

    imageBitMap?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentScale = ContentScale.Crop,
            modifier = modifier,
            contentDescription = null
        )
    }
}


@Composable
fun BoxCircle(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        content()

    }
}