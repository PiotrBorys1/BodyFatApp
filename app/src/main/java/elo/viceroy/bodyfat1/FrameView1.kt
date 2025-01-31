package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class FrameView1@JvmOverloads constructor(
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

        color=-926378346
        style=Paint.Style.FILL

    }
    val paint_line= Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color= Color.RED
        style=Paint.Style.FILL

    }

    var photosize=40
    var isleft=false
    var isright=false
    var top_start_of_thin_frame=40
    var bottom_end_of_thin_frame=40

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        setMeasuredDimension(photosize,MEASURED_SIZE_MASK)




    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

        if (canvas!=null)
        {
            if (isleft) {
                canvas.drawRect(left.toFloat(),top.toFloat(),right.toFloat()-10f,bottom.toFloat(),paint_rect)
                canvas.drawRect(right.toFloat()-10f,top_start_of_thin_frame.toFloat(),right.toFloat(),height.toFloat()-bottom_end_of_thin_frame.toFloat(),paint_line)
            }
            else if (isright)
            {
                canvas.drawRect(10f,top.toFloat(),width.toFloat(),bottom.toFloat(),paint_rect)
                canvas.drawRect(0f,top_start_of_thin_frame.toFloat(),10f,height.toFloat()-bottom_end_of_thin_frame.toFloat(),paint_line)
            }
        }

    }
}