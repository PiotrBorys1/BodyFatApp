package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class blackLineView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {
    init{
        isClickable = true
        isScrollContainer=true

    }
    val paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=Color.BLACK
        style=Paint.Style.FILL
        textSize=60f
        typeface=Typeface.SERIF
    }

    var i=0f
    var i_text=0f
    var finish=false
    var BMI_text:String?=null



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas!=null)
        {
            canvas.drawRect(i,(height/5).toFloat(),i+20f,(4*height/5).toFloat(),paint)
            if (finish)
            {
                canvas.drawText(BMI_text!!,i_text,((height/5)-2).toFloat(),paint)
            }
        }

    }
}