package com.example.scanner.ui.editor

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText

enum class ItemType { TEXT, BOX, TABLE }

class DraggableItemView @JvmOverloads constructor(
    context: Context, 
    val itemType: ItemType = ItemType.TEXT,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var lastX = 0f
    private var lastY = 0f
    private var isResizing = false

    var editText: AppCompatEditText? = null
    private val resizeHandle: ImageView

    init {
        when (itemType) {
            ItemType.TEXT -> {
                editText = AppCompatEditText(context).apply {
                    layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                        setMargins(0, 0, 40, 40)
                    }
                    background = null
                    hint = "Enter text"
                    setTextColor(Color.BLACK)
                }
                addView(editText)
            }
            ItemType.BOX -> {
                val boxView = android.view.View(context).apply {
                    layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                        setMargins(0, 0, 40, 40)
                    }
                    setBackgroundColor(Color.TRANSPARENT)
                    background = android.graphics.drawable.GradientDrawable().apply {
                        setStroke(5, Color.RED)
                    }
                }
                addView(boxView)
            }
            ItemType.TABLE -> {
                val tableLayout = TableLayout(context).apply {
                    layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                        setMargins(0, 0, 40, 40)
                    }
                    isStretchAllColumns = true
                }
                // Add 2x2 default table
                for (i in 0 until 2) {
                    val row = TableRow(context)
                    for (j in 0 until 2) {
                        val cell = AppCompatEditText(context).apply {
                            hint = "Cell"
                            background = android.graphics.drawable.GradientDrawable().apply {
                                setStroke(2, Color.BLACK)
                            }
                        }
                        row.addView(cell)
                    }
                    tableLayout.addView(row)
                }
                addView(tableLayout)
            }
        }

        // Add Resize Handle
        resizeHandle = ImageView(context).apply {
            layoutParams = LayoutParams(40, 40).apply {
                gravity = android.view.Gravity.BOTTOM or android.view.Gravity.END
            }
            setBackgroundColor(Color.BLUE)
        }
        addView(resizeHandle)

        setBackgroundColor(Color.parseColor("#110000FF")) // Faint selection
        
        setupTouchListeners()
    }

    private fun setupTouchListeners() {
        setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.rawX
                    lastY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.rawX - lastX
                    val dy = event.rawY - lastY

                    view.x += dx
                    view.y += dy

                    lastX = event.rawX
                    lastY = event.rawY
                    true
                }
                else -> false
            }
        }

        resizeHandle.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.rawX
                    lastY = event.rawY
                    isResizing = true
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isResizing) {
                        val dx = event.rawX - lastX
                        val dy = event.rawY - lastY

                        val newWidth = width + dx.toInt()
                        val newHeight = height + dy.toInt()

                        if (newWidth > 100 && newHeight > 50) {
                            layoutParams.width = newWidth
                            layoutParams.height = newHeight
                            requestLayout()
                        }

                        lastX = event.rawX
                        lastY = event.rawY
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    isResizing = false
                    true
                }
                else -> false
            }
        }
    }
}
