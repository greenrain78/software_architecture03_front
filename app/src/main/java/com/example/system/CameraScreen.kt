package com.example.system

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import android.Manifest
import com.google.accompanist.permissions.PermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureImageAndSendToAI() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    when (cameraPermissionState.status) {
        is PermissionStatus.Granted -> {
            CameraPreviewAndCapture(context, lifecycleOwner)
            Text("Camera permission granted!")
        }
        is PermissionStatus.Denied -> {
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request Camera Permission")
            }
        }
    }
}

@Composable
fun CameraPreviewAndCapture(context: Context, lifecycleOwner: LifecycleOwner) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val previewView = remember { PreviewView(context) }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    LaunchedEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.weight(1f)
        )

        CapturePhotoButton(imageCapture, context)
    }
}

@Composable
fun CapturePhotoButton(imageCapture: ImageCapture, context: Context) {
    Button(onClick = {
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            createTempFile(context)
        ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val photoFile = outputFileResults.savedUri?.toFile()

                    val bitmap = photoFile?.let {
                        BitmapFactory.decodeFile(it.absolutePath)
                    }

                    bitmap?.let { sendToAI(it) }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Camera", "Error taking photo: ${exception.message}")
                }
            }
        )
    }) {
        Text("Capture Image")
    }
}

fun createTempFile(context: Context): File {
    val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
    return tempFile
}

fun sendToAI(bitmap: Bitmap) {
    Log.d("AI", "Sending image to AI model...")
}