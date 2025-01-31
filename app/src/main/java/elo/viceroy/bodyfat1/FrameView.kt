package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class FrameView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {
init{
    isClickable = true
    isScrollContainer=true

}
    val paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setARGB(255,100,100,100)
    }
    val paint_rect= Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color= -926378346
        style=Paint.Style.FILL

    }
    val paint_line= Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color= Color.RED
        style=Paint.Style.FILL

    }

    var photosize=40
    var istop=false
    var isbottom=false
    var left_start_of_thin_frame=40
    var right_end_of_thin_frame=40

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

         setMeasuredDimension(MEASURED_SIZE_MASK,photosize)


    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

        if (canvas!=null)
        {
            if (istop) {
                canvas.drawRect(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat()-10f,paint_rect)
                canvas.drawRect(left_start_of_thin_frame.toFloat(),bottom.toFloat()-10f,width.toFloat()-right_end_of_thin_frame.toFloat(),bottom.toFloat(),paint_line)
            }
            else if (isbottom)
            {
                canvas.drawRect(left.toFloat(),10f,width.toFloat(),height.toFloat(),paint_rect)
                canvas.drawRect(left_start_of_thin_frame.toFloat(),0f,width.toFloat()-right_end_of_thin_frame.toFloat(),10f,paint_line)

            }
        }

    }
}