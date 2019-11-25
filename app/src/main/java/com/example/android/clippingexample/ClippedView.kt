package com.example.android.clippingexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ClippedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        strokeWidth = resources.getDimension(R.dimen.strokeWidth)
        textSize = resources.getDimension(R.dimen.textSize)
    }

    private val path = Path()

    private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
    private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
    private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
    private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)
    private val rectInset = resources.getDimension(R.dimen.rectInset)
    private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)
    private val circleRadius = resources.getDimension(R.dimen.circleRadius)
    private val textOffset = resources.getDimension(R.dimen.textOffset)
    private val textSize = resources.getDimension(R.dimen.textSize)

    //Setting up coordinates for columns
    private val columnOne = rectInset
    private val columnTwo = columnOne + rectInset + clipRectRight
    private val rowOne = rectInset
    private val rowTwo = rowOne + rectInset + clipRectBottom
    private val rowThree = rowTwo + rectInset + clipRectBottom
    private val rowFour = rowThree + rectInset + clipRectBottom
    private val textRow = rowFour + (1.5f * clipRectBottom)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackAndUnclippedRectangle(canvas)
        drawDifferenceClippingExample(canvas)
        drawCircularClippingExample(canvas)
        drawIntersectionClippingExample(canvas)
        drawCombinedClippingExample(canvas)
        drawRoundedRectangleClippingExample(canvas)
        drawOutsideClippingExample(canvas)
        drawSkewedTextExample(canvas)
        drawTranslatedTextExample(canvas)
        // drawQuickRejectExample(canvas)
    }

    //Function for Clipped Rectangle Properties
    private fun drawClippedRectangle(canvas: Canvas) {

        //Drawing a clipped Rectangle
        canvas.clipRect(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom)

        //Filling clipped rectangle with WHITE
        canvas.drawColor(Color.WHITE)

        //Taking RED Color for drawing a diagonal line
        paint.color = Color.RED

        //Drawing a diagonal line
        canvas.drawLine(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom, paint)

        //Taking GREEN Color to draw a circle
        paint.color = Color.GREEN

        //Drawing Circle
        canvas.drawCircle(circleRadius, clipRectBottom - circleRadius, circleRadius, paint)

        //Taking BLUE Color to draw text
        paint.color = Color.BLUE

        //Setting textSize
        paint.textSize = textSize

        //Setting Text Alignment
        paint.textAlign = Paint.Align.RIGHT

        //Writing text in Rectangle
        canvas.drawText(context.getString(R.string.clipping), clipRectRight, textOffset, paint)
    }

    private fun drawBackAndUnclippedRectangle(canvas: Canvas) {
        //Drawing the Background for Canvas
        canvas.drawColor(Color.GRAY)

        //Saving the Canvas
        canvas.save()

        //Setting position for drawing the Clipped Rectangle
        canvas.translate(columnOne, rowOne)

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
    }

    private fun drawDifferenceClippingExample(canvas: Canvas) {
    }

    private fun drawCircularClippingExample(canvas: Canvas) {
    }

    private fun drawIntersectionClippingExample(canvas: Canvas) {
    }

    private fun drawCombinedClippingExample(canvas: Canvas) {
    }

    private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
    }

    private fun drawOutsideClippingExample(canvas: Canvas) {
    }

    private fun drawTranslatedTextExample(canvas: Canvas) {
    }

    private fun drawSkewedTextExample(canvas: Canvas) {
    }

    private fun drawQuickRejectExample(canvas: Canvas) {
    }
}