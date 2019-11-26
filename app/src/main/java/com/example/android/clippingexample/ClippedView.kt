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

    private var rectF = RectF(
        rectInset,
        rectInset,
        clipRectRight - rectInset,
        clipRectBottom - rectInset
    )

    private val rejectRow = rowFour + rectInset + 2 * clipRectBottom

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
        drawQuickRejectExample(canvas)
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
        //Saving the canvas
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
                4 * rectInset, 4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset,
                Region.Op.DIFFERENCE
            )
        else {
            canvas.clipOutRect(
                4 * rectInset, 4 * rectInset,
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
        //Saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnOne, rowTwo)

        //Clears any lines and curves from the path but unlike reset(),
        path.rewind()

        //Keeps the internal data structure for faster reuse.
        path.addCircle(
            circleRadius,
            clipRectBottom - circleRadius,
            circleRadius,
            Path.Direction.CCW
        )

        // The method clipPath(path, Region.Op.DIFFERENCE) was deprecated in
        // API level 26. The recommended alternative method is
        // clipOutPath(Path), which is currently available in
        // API level 26 and higher.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipPath(path, Region.Op.DIFFERENCE)
        } else {
            canvas.clipOutPath(path)
        }

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
    }

    private fun drawIntersectionClippingExample(canvas: Canvas) {
        //Saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnTwo, rowTwo)

        //Drawing the clipped Rectangle
        canvas.clipRect(
            clipRectLeft,
            clipRectTop,
            clipRectRight - smallRectOffset,
            clipRectBottom - smallRectOffset
        )

        // The method clipRect(float, float, float, float, Region.Op
        // .INTERSECT) was deprecated in API level 26. The recommended
        // alternative method is clipRect(float, float, float, float), which
        // is currently available in API level 26 and higher.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipRect(
                clipRectLeft + smallRectOffset,
                clipRectTop + smallRectOffset,
                clipRectRight, clipRectBottom,
                Region.Op.INTERSECT
            )
        } else {
            canvas.clipRect(
                clipRectLeft + smallRectOffset,
                clipRectTop + smallRectOffset,
                clipRectRight, clipRectBottom
            )
        }

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
    }

    private fun drawCombinedClippingExample(canvas: Canvas) {
        //Saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnOne, rowThree)

        //Clears any lines and curves from the path but unlike reset(),
        path.rewind()

        //Keeps the internal data structure for faster reuse.
        //A Circle
        path.addCircle(
            clipRectLeft + rectInset + circleRadius,
            clipRectTop + circleRadius + rectInset,
            circleRadius, Path.Direction.CCW
        )

        //A Rectangle
        path.addRect(
            clipRectRight / 2 - circleRadius,
            clipRectTop + circleRadius + rectInset,
            clipRectRight / 2 + circleRadius,
            clipRectBottom - rectInset, Path.Direction.CCW
        )

        //Clipping the path
        canvas.clipPath(path)

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
    }

    private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
        //Saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnTwo, rowThree)

        //Clears any lines and curves from the path but unlike reset(),
        path.rewind()

        //Keeps the internal data structure for faster reuse.
        //A Rounded Rectangle
        path.addRoundRect(
            rectF, clipRectRight / 4,
            clipRectRight / 4, Path.Direction.CCW
        )

        //Clipping the path
        canvas.clipPath(path)

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
    }

    private fun drawOutsideClippingExample(canvas: Canvas) {
        //Saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnOne, rowFour)

        //Drawing the clipped Rectangle
        canvas.clipRect(
            2 * rectInset, 2 * rectInset,
            clipRectRight - 2 * rectInset,
            clipRectBottom - 2 * rectInset
        )

        //Draw the Clipped Rectangle with Properties defined in *drawClippedRectangle()*
        drawClippedRectangle(canvas)

        //Restore the state of Rectangle
        canvas.restore()
    }

    private fun drawTranslatedTextExample(canvas: Canvas) {
        //Saving the canvas
        canvas.save()

        //Taking GREEN Color to draw text
        paint.color = Color.GREEN

        //Setting Text Alignment
        paint.textAlign = Paint.Align.LEFT

        //Moving the coordinates
        canvas.translate(columnTwo, textRow)

        // Draw text.
        canvas.drawText(
            context.getString(R.string.translated),
            clipRectLeft, clipRectTop, paint
        )

        //Restore the state of Text
        canvas.restore()
    }

    private fun drawSkewedTextExample(canvas: Canvas) {
        //Saving the canvas
        canvas.save()

        //Taking YELLOW Color to draw skewed text
        paint.color = Color.YELLOW

        //Setting Text Alignment
        paint.textAlign = Paint.Align.RIGHT

        //Moving the coordinates
        canvas.translate(columnTwo, textRow)

        // Apply skew transformation.
        canvas.skew(0.2f, 0.3f)

        // Draw text.
        canvas.drawText(
            context.getString(R.string.skewed),
            clipRectLeft, clipRectTop, paint
        )

        //Restore the state of Text
        canvas.restore()
    }

    private fun drawQuickRejectExample(canvas: Canvas) {
        val inClipRectangle = RectF(
            clipRectRight / 2,
            clipRectBottom / 2,
            clipRectRight * 2,
            clipRectBottom * 2
        )

        val notInClipRectangle = RectF(
            RectF(
                clipRectRight + 1,
                clipRectBottom + 1,
                clipRectRight * 2,
                clipRectBottom * 2
            )
        )

        //Saving the canvas
        canvas.save()

        //Moving the coordinates
        canvas.translate(columnOne, rejectRow)

        //Drawing the clipped Rectangle
        canvas.clipRect(
            clipRectLeft, clipRectTop,
            clipRectRight, clipRectBottom
        )

        //Pass in *inClipRectangle* or *notInClipRectangle*
        if (canvas.quickReject(
                inClipRectangle, Canvas.EdgeType.AA
            )
        ) {
            canvas.drawColor(Color.WHITE)
        } else {
            canvas.drawColor(Color.BLACK)
            canvas.drawRect(
                inClipRectangle, paint
            )
        }

        //Restore the state of Rectangle
        canvas.restore()
    }
}