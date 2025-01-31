package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.ThumbnailUtils
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class NattyClockView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {
    init{
        isClickable = true
        isScrollContainer=true

    }
 var bitmap_needle:Bitmap?=null
    var bitmap_needle1:Bitmap?=null
 var rotate_degres=0f
    var meitrix=Matrix()
    lateinit var rect:RectF

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

            if (canvas!=null)
            {
                if (bitmap_needle!=null)
                {

                    bitmap_needle1=ThumbnailUtils.extractThumbnail(bitmap_needle,6*width/20,height/7)
                    meitrix.setTranslate((225*width/1000).toFloat(),(650*height/1000).toFloat())
                    meitrix.postRotate(rotate_degres,(495*width/1000).toFloat(),(736*height/1000).toFloat())
                   /* canvas.drawBitmap(bitmap_needle!!,null,rect,Paint())*/
                    canvas.drawBitmap(bitmap_needle1!!,meitrix,Paint())
                    meitrix.reset()

                }
            }

    }
}