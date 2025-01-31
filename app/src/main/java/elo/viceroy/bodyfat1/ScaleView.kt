package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class ScaleView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {
    init{
        isClickable = true
        isScrollContainer=true

    }

    var adult_age=true



    /*lime */
    val paint_green= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color= Color.GREEN
        style=Paint.Style.FILL
    }
    /* pale blue*/
    val paint_blue= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color= Color.CYAN
        style=Paint.Style.FILL
    }
/*orange*/
    val paint_orange= Paint(Paint.ANTI_ALIAS_FLAG).apply {
    color= Color.MAGENTA
    style=Paint.Style.FILL
    }
    /*red*/
    val paint_red= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color= Color.RED
        style=Paint.Style.FILL
    }
    /*dark red*/
    val paint_dark_red= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setARGB(255,120,0,0)
        style=Paint.Style.FILL
    }
    var tp=0
    var btm=0
    var rght=0
    var lft=0
    var wdth=0
    var hght=0
val textSixze=30f

    val paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {

        color= Color.BLACK
        style=Paint.Style.FILL
        textSize=textSixze
        typeface=Typeface.DEFAULT_BOLD

    }




    /*override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        wdth=w
        hght=h
    }*/

    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }*/

    /*override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        tp=top
        btm=bottom
        lft=left
        rght=right
    }*/
    fun drawBlackline(canvas1:Canvas)
    {




    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        rght=width
        btm=height
        wdth=width
        hght=height
        if (canvas!=null) {
            if (adult_age) {

                canvas.drawRect(
                    lft.toFloat(),
                    (tp + hght/5).toFloat(),
                    (rght / 8).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_blue
                )
                canvas.drawText("Underweight", lft.toFloat(), (btm - hght/5+textSixze.toInt()).toFloat(), paint)
                canvas.drawRect(
                    (rght / 8).toFloat(),
                    (tp + hght/5).toFloat(),
                    (2 * rght / 5).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_green
                )
                canvas.drawText("Normal", (rght / 5).toFloat(), (btm - hght/5+textSixze.toInt()).toFloat(), paint)
                canvas.drawRect(
                    (2 * rght / 5).toFloat(),
                    (tp + hght/5).toFloat(),
                    (3 * rght / 5).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_orange
                )
                canvas.drawText("Overweight", (2 * rght / 5).toFloat(), (btm - hght/5+textSixze.toInt()).toFloat(), paint)
                canvas.drawRect(
                    (3 * rght / 5).toFloat(),
                    (tp + hght/5).toFloat(),
                    (4 * rght / 5).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_red
                )
                canvas.drawText("Obese", (3 * rght / 5).toFloat(), (btm - hght/5+textSixze.toInt()).toFloat(), paint)
                canvas.drawRect(
                    (4 * rght / 5).toFloat(),
                    (tp + hght/5).toFloat(),
                    rght.toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_dark_red
                )
                canvas.drawText(
                    "Extreme obese",
                    (4 * rght / 5).toFloat(),
                    (btm - hght/5+textSixze.toInt()).toFloat(),
                    paint
                )
            } else {

                canvas.drawRect(
                    lft.toFloat(),
                    (tp + hght/5).toFloat(),
                    (rght / 20).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_blue
                )
                canvas.drawText("Underweight", lft.toFloat(), (btm - hght/5+textSixze.toInt()).toFloat(), paint)
                canvas.drawRect(
                    (rght / 20).toFloat(),
                    (tp + hght/5).toFloat(),
                    (17 * rght / 20).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_green
                )
                canvas.drawText("Normal", (10 * rght / 20).toFloat(), (btm - hght/5+textSixze.toInt()).toFloat(), paint)
                canvas.drawRect(
                    (17 * rght / 20).toFloat(),
                    (tp + hght/5).toFloat(),
                    (19 * rght / 20).toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_orange
                )
                canvas.drawText(
                    "Overweight",
                    (16 * rght / 20).toFloat(),
                    (btm - hght/5+textSixze.toInt()).toFloat(),
                    paint
                )
                canvas.drawRect(
                    (19 * rght / 20).toFloat(),
                    (tp + hght/5).toFloat(),
                    rght.toFloat(),
                    (btm - hght/5).toFloat(),
                    paint_red
                )


            }



        }

    }


}