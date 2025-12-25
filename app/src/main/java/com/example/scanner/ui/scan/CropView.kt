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

    private val cropRect = RectF()
    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }
    private val overlayPaint = Paint().apply {
        color = Color.parseColor("#80000000") // Semi-transparent black
        style = Paint.Style.FILL
    }
    private val handlePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val handleRadius = 30f
    private var activeHandle: Handle? = null
    private var lastX = 0f
    private var lastY = 0f

    enum class Handle {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER
    }

    init {
        // Initialize crop rect to 80% of view size (will be adjusted in onSizeChanged)
        post {
            val margin = width * 0.1f
            cropRect.set(margin, margin, width - margin, height - margin)
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Initialize crop rect if not set
        if (cropRect.isEmpty) {
            val margin = w * 0.1f
            cropRect.set(margin, margin, w - margin, h - margin)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw overlay (dimmed area outside crop rect)
        canvas.drawRect(0f, 0f, width.toFloat(), cropRect.top, overlayPaint)
        canvas.drawRect(0f, cropRect.top, cropRect.left, cropRect.bottom, overlayPaint)
        canvas.drawRect(cropRect.right, cropRect.top, width.toFloat(), cropRect.bottom, overlayPaint)
        canvas.drawRect(0f, cropRect.bottom, width.toFloat(), height.toFloat(), overlayPaint)

        // Draw crop rectangle
        canvas.drawRect(cropRect, paint)

        // Draw corner handles
        canvas.drawCircle(cropRect.left, cropRect.top, handleRadius, handlePaint)
        canvas.drawCircle(cropRect.right, cropRect.top, handleRadius, handlePaint)
        canvas.drawCircle(cropRect.left, cropRect.bottom, handleRadius, handlePaint)
        canvas.drawCircle(cropRect.right, cropRect.bottom, handleRadius, handlePaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                activeHandle = getHandleAt(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastX
                val dy = event.y - lastY

                when (activeHandle) {
                    Handle.TOP_LEFT -> {
                        cropRect.left = (cropRect.left + dx).coerceIn(0f, cropRect.right - 100f)
                        cropRect.top = (cropRect.top + dy).coerceIn(0f, cropRect.bottom - 100f)
                    }
                    Handle.TOP_RIGHT -> {
                        cropRect.right = (cropRect.right + dx).coerceIn(cropRect.left + 100f, width.toFloat())
                        cropRect.top = (cropRect.top + dy).coerceIn(0f, cropRect.bottom - 100f)
                    }
                    Handle.BOTTOM_LEFT -> {
                        cropRect.left = (cropRect.left + dx).coerceIn(0f, cropRect.right - 100f)
                        cropRect.bottom = (cropRect.bottom + dy).coerceIn(cropRect.top + 100f, height.toFloat())
                    }
                    Handle.BOTTOM_RIGHT -> {
                        cropRect.right = (cropRect.right + dx).coerceIn(cropRect.left + 100f, width.toFloat())
                        cropRect.bottom = (cropRect.bottom + dy).coerceIn(cropRect.top + 100f, height.toFloat())
                    }
                    Handle.CENTER -> {
                        val newLeft = cropRect.left + dx
                        val newRight = cropRect.right + dx
                        val newTop = cropRect.top + dy
                        val newBottom = cropRect.bottom + dy

                        if (newLeft >= 0 && newRight <= width) {
                            cropRect.left = newLeft
                            cropRect.right = newRight
                        }
                        if (newTop >= 0 && newBottom <= height) {
                            cropRect.top = newTop
                            cropRect.bottom = newBottom
                        }
                    }
                    else -> return false
                }

                lastX = event.x
                lastY = event.y
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                activeHandle = null
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun getHandleAt(x: Float, y: Float): Handle? {
        // Check corner handles first
        if (abs(x - cropRect.left) < handleRadius * 2 && abs(y - cropRect.top) < handleRadius * 2) {
            return Handle.TOP_LEFT
        }
        if (abs(x - cropRect.right) < handleRadius * 2 && abs(y - cropRect.top) < handleRadius * 2) {
            return Handle.TOP_RIGHT
        }
        if (abs(x - cropRect.left) < handleRadius * 2 && abs(y - cropRect.bottom) < handleRadius * 2) {
            return Handle.BOTTOM_LEFT
        }
        if (abs(x - cropRect.right) < handleRadius * 2 && abs(y - cropRect.bottom) < handleRadius * 2) {
            return Handle.BOTTOM_RIGHT
        }
        // Check if inside rectangle (for moving)
        if (cropRect.contains(x, y)) {
            return Handle.CENTER
        }
        return null
    }

    fun getCropRect(): RectF {
        return RectF(cropRect)
    }

    fun setCropRect(rect: RectF) {
        cropRect.set(rect)
        invalidate()
    }
}
