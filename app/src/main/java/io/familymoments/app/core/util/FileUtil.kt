package io.familymoments.app.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    private const val COMPRESS_QUALITY = 50

    suspend fun imageFilesResize(
        context: Context,
        uriList: List<Uri>
    ): List<File> {
        val pathHashMap = hashMapOf<Int, String?>()

        CoroutineScope(Dispatchers.IO).launch {
            uriList.forEachIndexed { index, uri ->
                launch {
                    val path = optimizeBitmap(context, uri, index)
                    pathHashMap[index] = path
                }
            }
        }.join()

        return pathHashMap.map { File(it.value!!) }
    }

    private fun optimizeBitmap(context: Context, uri: Uri, index: Int): String? {
        try {
            val tempFile = File(context.cacheDir, "image$index.jpg")
            tempFile.createNewFile() // 임시 파일 생성

            // 지정된 이름을 가진 파일에 쓸 파일 출력 스트림을 만든다.
            val fos = FileOutputStream(tempFile)

            convertUriToBitmap(uri, context).apply {
                compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, fos)
                recycle()
            }

            fos.flush()
            fos.close()

            return tempFile.absolutePath // 임시파일 저장경로 리턴

        } catch (e: Exception) {
            Timber.tag("hkhk").e("FileUtil - ${e.message}")
        }

        return null
    }

    @Suppress("DEPRECATION")
    fun convertUriToBitmap(uri: Uri?, context: Context): Bitmap {
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
        return bitmap ?: throw NullPointerException()
    }


    suspend fun convertUrlToBitmap(url: String, context: Context): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val loader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .build()
                val result = (loader.execute(request) as SuccessResult).drawable
                (result as BitmapDrawable).bitmap
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun convertBitmapToFile(bitmap: Bitmap, context: Context): File {
        val file = File(context.cacheDir, "image.jpg")
        file.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, outputStream)
        }
        return file
    }
}
