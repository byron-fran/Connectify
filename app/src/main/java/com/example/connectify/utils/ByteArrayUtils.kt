package com.example.connectify.utils

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.core.net.toUri

object ByteArrayUtils {

    fun getByteArray(uri: String?, context: Context): ByteArray? {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, uri?.toUri())

            return retriever.embeddedPicture

        } catch (_: Throwable) {
            return null
        } finally {
            retriever.release()
        }
    }
}