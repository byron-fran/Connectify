package com.example.connectify.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

object BitMapUtils  {

    /**
     * Decodes a [Bitmap] from a [ByteArray] with subsampling to reduce memory usage.
     *
     * This method first decodes the image bounds without loading the entire image into memory.
     * Then, it calculates an appropriate `inSampleSize` to scale down the image to fit
     * the requested width and height, while maintaining aspect ratio. Finally, it decodes
     * the image with the calculated `inSampleSize`.
     *
     * @param byteArray The [ByteArray] containing the image data.
     * @param reqWidth The required width of the decoded [Bitmap].
     * @param reqHeight The required height of the decoded [Bitmap].
     * @return The decoded [Bitmap] with subsampling, or `null` if decoding fails.
     */
    fun decodeBitmapWithSubsampling(byteArray: ByteArray, reqWidth: Int, reqHeight: Int): Bitmap? {

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        options.inJustDecodeBounds = false

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
    }

    /**
     * Calculates the `inSampleSize` value for [BitmapFactory.Options] to efficiently decode
     * an image by subsampling. This value is a power of 2 and determines how much to scale down
     * the image.
     *
     * The `inSampleSize` is calculated such that the decoded image will be as close as possible to
     * the requested width and height, without exceeding them, while maintaining the original aspect ratio.
     *
     * @param options The [BitmapFactory.Options] object that has already been used to decode
     *                the image bounds (i.e., `inJustDecodeBounds = true` was set).
     *                The `outWidth` and `outHeight` properties of this object are used.
     * @param reqWidth The target width for the decoded image.
     * @param reqHeight The target height for the decoded image.
     * @return The calculated `inSampleSize` value. This will be a power of 2 (e.g., 1, 2, 4, 8).
     *         Returns 1 if no subsampling is needed (i.e., the image is already smaller than or
     *         equal to the requested dimensions).
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}