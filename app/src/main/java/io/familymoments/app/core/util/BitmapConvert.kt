package io.familymoments.app.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
fun convertUriToBitmap(uri:Uri?,context: Context): Bitmap? {
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

fun convertBitmapToFile(bitmap: Bitmap?): File {
    val file = File.createTempFile("profile_image", ".jpg") // 임시 파일 생성
    val outputStream = FileOutputStream(file)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    outputStream.flush()
    outputStream.close()
    return file
}
