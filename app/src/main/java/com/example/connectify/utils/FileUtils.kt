package com.example.connectify.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object FileUtils {


    fun copiUtiToInternalStorage(context : Context, sourceUri : Uri) : Uri? {

        return try {
            context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->

                val fileName = "img_${System.currentTimeMillis()}.jpg"
                val destinationFile = File(context.filesDir, fileName)

                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                FileProvider.getUriForFile(context, "${context.packageName}.provider", destinationFile)
            }
        } catch (e: Exception) {
            Log.e("FileUtils", "Error al copiar la URI al almacenamiento interno", e)
            null
        }
    }
}