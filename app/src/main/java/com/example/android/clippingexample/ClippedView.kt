package com.example.android.clippingexample

import android.content.Context
import android.graphics.*
import android.os.Build
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
        //saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnTwo, rowOne)

        //Use the subtraction of two clipping rectangle to create a frame
        canvas.clipRect(
            2 * rectInset,
            2 * rectInset,
            clipRectRight - 2 * rectInset,
            clipRectBottom - 2 * rectInset
        )

        // The method clipRect(float, float, float, float, Region.Op
        // .DIFFERENCE) was deprecated in API level 26. The recommended
        // alternative method is clipOutRect(float, float, float, float),
        // which is currently available in API level 26 and higher.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            canvas.clipRect(
                4 * rectInset,4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset,
                Region.Op.DIFFERENCE
            )
        else {
            canvas.clipOutRect(
                4 * rectInset,4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset
            )
        }

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
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