package com.example.scanner.ui.scan

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class CropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val cropPoints = Array(4) { android.graphics.PointF() }
    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }
    private val cornerPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val overlayPaint = Paint().apply {
        color = Color.parseColor("#80000000") // Semi-transparent black
        style = Paint.Style.FILL
    }
    
    private val handleRadius = 40f
    private val touchTolerance = 60f
    private var activeHandleIndex = -1 // 0: TL, 1: TR, 2: BR, 3: BL
    
    private var bitmapRect = RectF()
    
    init {
        // Initialize points will happen in onSizeChanged or when image is set
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (cropPoints[0].x == 0f && cropPoints[0].y == 0f) {
            resetPoints()
        }
    }
    
    fun setImageEvent(imageRect: RectF?) {
        if (imageRect != null) {
            bitmapRect.set(imageRect)
            resetPoints()
            invalidate()
        }
    }
    
    fun resetPoints() {
        if (bitmapRect.isEmpty) {
            val margin = width * 0.1f
            cropPoints[0].set(margin, margin) // TL
            cropPoints[1].set(width - margin, margin) // TR
            cropPoints[2].set(width - margin, height - margin) // BR
            cropPoints[3].set(margin, height - margin) // BL
        } else {
            val margin = 20f
            cropPoints[0].set(bitmapRect.left + margin, bitmapRect.top + margin)
            cropPoints[1].set(bitmapRect.right - margin, bitmapRect.top + margin)
            cropPoints[2].set(bitmapRect.right - margin, bitmapRect.bottom - margin)
            cropPoints[3].set(bitmapRect.left + margin, bitmapRect.bottom - margin)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw overlay (simplified for 4-point: just darken everything and clip out the path?)
        // For proper "inverse" path clipping on older Android versions, we might need a different approach.
        // A simpler visual trick is to draw the polygon with a clear porter-duff mode on a dark layer,
        // but here we'll just draw the lines and handles for simplicity as "darkening outside" a non-rect is complex.
        
        // Draw connecting lines
        for (i in 0 until 4) {
             val start = cropPoints[i]
             val end = cropPoints[(i + 1) % 4]
             canvas.drawLine(start.x, start.y, end.x, end.y, paint)
        }

        // Draw corner handles
        for (point in cropPoints) {
            canvas.drawCircle(point.x, point.y, handleRadius, cornerPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                activeHandleIndex = getHandleIndex(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (activeHandleIndex != -1) {
                    // constrain movement close to bounds if needed, for now free movement
                    cropPoints[activeHandleIndex].set(event.x, event.y)
                    invalidate()
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                activeHandleIndex = -1
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getHandleIndex(x: Float, y: Float): Int {
        for (i in cropPoints.indices) {
            val p = cropPoints[i]
            if (abs(x - p.x) < touchTolerance && abs(y - p.y) < touchTolerance) {
                return i
            }
        }
        return -1
    }

    fun getCropPoints(): List<android.graphics.PointF> {
        return cropPoints.toList()
    }
}
