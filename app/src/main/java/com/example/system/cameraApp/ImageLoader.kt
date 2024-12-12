package com.example.system.cameraApp

import android.net.Uri
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher

class ImageLoader() {
    private var imageUri: Uri = Uri.EMPTY
    private var cameraApp: CameraApp = CameraApp()

    fun captureImage(
        context: Context,
        cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>,
    ) {
        cameraApp.capture(context, cameraLauncher) { uri ->
            imageUri = uri
        }
    }

    fun getImageUri(): Uri {
        return imageUri
    }
}