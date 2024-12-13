package com.example.system

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.system.cameraApp.CameraApp
import com.example.system.cameraApp.ImageLoader
import com.example.system.ui.viewmodel.IngredientViewModel
import java.io.InputStream

fun Uri.toBitmap(contentResolver: ContentResolver): Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(this)
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        Log.e("CameraScreen", "Error while converting Uri to Bitmap: ${e.message}")
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}

@Composable
fun CameraScreen(ingredientViewModel: IngredientViewModel) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    //val capturedIngredient by ingredientViewModel.capturedIngredient.collectAsState()

    // 카메라 권한을 요청할 ActivityResultLauncher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("CameraScreen", "Camera permission granted")
        } else {
            Log.d("CameraScreen", "Camera permission denied")
        }
    }

    // 카메라 권한 확인 및 요청
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        // ActivityResultLauncher 설정
        val takePhotoLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { takenPhoto: Bitmap? ->
            if (takenPhoto != null) {

                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_${System.currentTimeMillis()}.jpg")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                if (uri != null) {
                    // Bitmap 사진 화면에 표시용
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        takenPhoto.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream.flush()
                    }

                    val bitmap = uri.toBitmap(context.contentResolver)
                    imageBitmap = bitmap

                    ingredientViewModel.recognizeIngredientFromImage()

                    if (bitmap == null) {
                        Log.e("CameraScreen", "Failed to load Bitmap from Uri")
                    }
                } else {
                    Log.e("CameraScreen", "Failed to capture image.")
                }
            } else {
                Log.e("CameraScreen", "Photo capture was canceled.")
            }

        }

        // 권한이 승인된 경우
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                modifier = Modifier.width(100.dp)
                    .height(100.dp),
                onClick = {
                // 권한이 승인된 상태에서 카메라 촬영을 실행
                //ingredientViewModel.getCapturedIngredient(context, takePhotoLauncher)
            }) {
                Column {
                    Text("Take Photo")
                    //Text("Ingredient : ${capturedIngredient.name}")
                }
            }

            imageBitmap?.let { bitmap ->
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Captured Image"
                )
            }
        }
    } else {
        // 권한이 승인되지 않은 경우 권한을 요청
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}