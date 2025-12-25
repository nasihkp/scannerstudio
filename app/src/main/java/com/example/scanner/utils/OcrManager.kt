package com.example.scanner.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

object OcrManager {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun processImage(bitmap: Bitmap): Text {
        val image = InputImage.fromBitmap(bitmap, 0)
        return recognizer.process(image).await()
    }
}
