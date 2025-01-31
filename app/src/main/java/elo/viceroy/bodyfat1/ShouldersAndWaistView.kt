package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.ThumbnailUtils
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class ShouldersAndWaistView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {
    init{
        isClickable = true
        isScrollContainer=true

    }
    val paint_red= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color= Color.RED
        style=Paint.Style.FILL
    }
    val paint_transparent= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=855576592
        style=Paint.Style.FILL
    }
    val paint_text= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=1681129471
        style=Paint.Style.FILL

    }
    var heightstart=50f
    var rangeheight=200f
    var rangewidth=600f
    var sidethickness=30f
    var widthstart=20f
    var text=""
    var viewwidth=0
    var viewheight=0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewwidth=MeasureSpec.getSize(widthMeasureSpec)
        viewheight=MeasureSpec.getSize(heightMeasureSpec)
        heightstart=(viewheight/10).toFloat()
        rangeheight=(viewheight/5).toFloat()
        sidethickness=(viewwidth/15).toFloat()
        widthstart=(viewwidth/10).toFloat()
        rangewidth=(viewwidth/3).toFloat()

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

        if (canvas!=null)
        {
            viewwidth=width
            viewheight=height
            paint_text.textSize=rangewidth/7f
          canvas.drawRect(widthstart,heightstart,sidethickness+widthstart,heightstart+rangeheight,
              paint_red
          )
            canvas.drawRect(widthstart+sidethickness+rangewidth,
                heightstart,widthstart+2*sidethickness+rangewidth,heightstart+rangeheight,paint_red)
            canvas.drawRect(widthstart+sidethickness,
                heightstart,widthstart+sidethickness+rangewidth,heightstart+rangeheight,paint_transparent)
            canvas.drawText(text,widthstart+sidethickness+rangewidth/text.length+50f,heightstart+6*rangeheight/10,paint_text)
        }

    }
}