package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.*
import android.media.ThumbnailUtils
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class CustomLayout@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):ConstraintLayout(context,attrs,defStyleAttr) {
    init{
        isClickable = true
        isScrollContainer=true

    }
    val paint_green= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color= Color.GREEN
        style=Paint.Style.FILL
    }
    val pintToBtmp=Paint()
    var layout_width=0
    var layout_height=0
    var btmp=BitmapFactory.decodeResource(resources,R.drawable.popupview)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        if (canvas!=null)
        {
            canvas.drawBitmap(btmp,0f,0f,pintToBtmp)

        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        layout_height=heightMeasureSpec
        layout_width=widthMeasureSpec
        btmp=ThumbnailUtils.extractThumbnail(btmp,MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec))
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }




}