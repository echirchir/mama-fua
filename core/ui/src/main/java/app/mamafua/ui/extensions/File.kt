package app.mamafua.ui.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

fun drawableToFile(context: Context, drawableResId: Int): File {
    val drawable = ContextCompat.getDrawable(context, drawableResId)!!
    val bitmap = (drawable as BitmapDrawable).bitmap

    val file = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
    }
    return file
}

fun uriToFile(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val fileName = getFileNameFromUri(context, uri) ?: "temp_file"
    val tempFile = File(context.cacheDir, fileName)

    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun getImageBitmapFromPath(context: Context, path: String): ImageBitmap? {
    val file = File(path)
    if (!file.exists()) return null

    return try {
        val displayMetrics = context.resources.displayMetrics
        val targetWidth = displayMetrics.widthPixels

        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(path, options)

        val srcWidth = options.outWidth
        val srcHeight = options.outHeight

        options.inSampleSize = calculateInSampleSize(
            srcWidth,
            srcHeight,
            targetWidth,
            displayMetrics.heightPixels
        )
        options.inJustDecodeBounds = false

        // Step 4: decode bitmap with downsampling
        val bitmap = BitmapFactory.decodeFile(path, options) ?: return null

        val exif = ExifInterface(path)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90       -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180      -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270      -> matrix.postRotate(270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.preScale(-1f, 1f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL   -> matrix.preScale(1f, -1f)
            ExifInterface.ORIENTATION_TRANSPOSE       -> { matrix.postRotate(90f);  matrix.preScale(-1f, 1f) }
            ExifInterface.ORIENTATION_TRANSVERSE      -> { matrix.postRotate(270f); matrix.preScale(-1f, 1f) }
        }

        val corrected = if (!matrix.isIdentity) {
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else bitmap

        if (corrected !== bitmap) bitmap.recycle()
        corrected.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun calculateInSampleSize(srcWidth: Int, srcHeight: Int, reqWidth: Int, reqHeight: Int): Int {
    var inSampleSize = 1

    if (srcHeight > reqHeight || srcWidth > reqWidth) {
        val halfHeight = srcHeight / 2
        val halfWidth = srcWidth / 2

        while ((halfHeight / inSampleSize) >= reqHeight &&
            (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

internal fun getFileNameFromUri(context: Context, uri: Uri): String? {
    var name: String? = null
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index != -1) {
                name = it.getString(index)
            }
        }
    }
    return name
}


