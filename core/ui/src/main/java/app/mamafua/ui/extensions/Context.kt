package app.mamafua.ui.extensions

import android.Manifest
import android.app.Instrumentation
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import app.mamafua.ui.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.openMailAvailabilityClient(to: String, subject: String? = null): Result<Unit> {
    return try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "vnd.android.cursor.item/email"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(intent)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

fun Context.checkCameraPermissionAndLaunch(
    imageUri: Uri?,
    launcher: ManagedActivityResultLauncher<Uri, Boolean>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    val cameraPermission = Manifest.permission.CAMERA
    if (ContextCompat.checkSelfPermission(this, cameraPermission) == PackageManager.PERMISSION_GRANTED) {
        if (imageUri != null) {
            launcher.launch(imageUri)
        } else {
            Toast.makeText(this, "Image Uri is null", Toast.LENGTH_SHORT).show()
        }
    } else {
        permissionLauncher.launch(cameraPermission)
    }
}

fun Context.getCountryFlag(iso: String): String {
    return try {
        if (iso.length >= 2) {
            val firstLetter = Character.codePointAt(iso.uppercase(), 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(iso.uppercase(), 1) - 0x41 + 0x1F1E6
            String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        } else ""
    } catch (e: Exception) {
        ""
    }
}

fun Context.createImageUri(): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "camera_image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

fun Context.isBiometricsAvailableOrEnabled(): Boolean {
    val biometricManager = BiometricManager.from(this)
    return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
        else -> false
    }
}

fun Context.showBiometricPrompt(onSuccess: () -> Unit, onError: () -> Unit) {
    (this as? FragmentActivity)?.let { activity ->
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt =
            BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError()
                }
            })
        biometricPrompt.authenticate(
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(application.getString(R.string.biometric_prompt_title))
                .setSubtitle(application.getString(R.string.biometric_prompt_description))
                .setConfirmationRequired(false)
                .setNegativeButtonText(application.getString(R.string.cancel))
                .build()
        )
    }
}
