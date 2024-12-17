package com.example.system.cameraApp

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import kotlinx.coroutines.coroutineScope

class CameraApp {
    fun capture(
        context: Context,
        cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>,
        onPhotoCaptured: (Uri) -> Unit
    ) {
        cameraLauncher.launch().apply {
                val contentValues = ContentValues().apply {
                    put(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        "captured_image_${System.currentTimeMillis()}.jpg"
                    )
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )

                if (imageUri != null) {
                    onPhotoCaptured(imageUri)
                }
        }
    }
}