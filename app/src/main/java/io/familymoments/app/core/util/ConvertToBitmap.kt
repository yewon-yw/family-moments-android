package io.familymoments.app.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

@Suppress("DEPRECATION")
fun convertToBitmap(context: Context, uri: Uri?): Bitmap? {
    var bitmap: Bitmap? = null
    uri?.let {
        bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source)
        }
    }
    return bitmap
}
