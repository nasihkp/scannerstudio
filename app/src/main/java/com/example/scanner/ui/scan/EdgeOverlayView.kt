package com.example.scanner.ui.scan

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class EdgeOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.holo_blue_light)
        style = Paint.Style.STROKE
        strokeWidth = 8f
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }
    
    // Draw 4 corners
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val w = width.toFloat()
        val h = height.toFloat()
        val margin = 100f
        val cornerLength = 60f
        
        // Top Left
        canvas.drawLine(margin, margin, margin + cornerLength, margin, paint)
        canvas.drawLine(margin, margin, margin, margin + cornerLength, paint)
        
        // Top Right
        canvas.drawLine(w - margin, margin, w - margin - cornerLength, margin, paint)
        canvas.drawLine(w - margin, margin, w - margin, margin + cornerLength, paint)
        
        // Bottom Left
        canvas.drawLine(margin, h - margin, margin + cornerLength, h - margin, paint)
        canvas.drawLine(margin, h - margin, margin, h - margin - cornerLength, paint)
        
        // Bottom Right
        canvas.drawLine(w - margin, h - margin, w - margin - cornerLength, h - margin, paint)
        canvas.drawLine(w - margin, h - margin, w - margin, h - margin - cornerLength, paint)
    }
}
