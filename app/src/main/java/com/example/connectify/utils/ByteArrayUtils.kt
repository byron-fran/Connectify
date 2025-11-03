package com.example.connectify.utils

import android.content.Context
import android.util.Log
import androidx.core.net.toUri

object ByteArrayUtils {

    fun getByteArray(uriString: String?, context: Context): ByteArray? {

        if (uriString == null) return null

        return try {
            val uri = uriString.toUri() // Usa Uri.parse() que es el estándar
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        } catch (e: Exception) {
            // Es buena idea registrar el error para depuración
            Log.e("ByteArrayUtils", "Error al leer bytes de la URI: $uriString", e)
            null
        }
    }
}
