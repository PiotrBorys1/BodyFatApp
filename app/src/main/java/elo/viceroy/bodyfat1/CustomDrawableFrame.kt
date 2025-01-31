package elo.viceroy.bodyfat1

import android.graphics.*
import android.graphics.drawable.Drawable

class CustomDrawableFrame:Drawable() {
var rectangle=Rect(100,100,100,100)
    val paint= Paint().apply {
        setARGB(255,100,100,100)
    }
    override fun draw(p0: Canvas) {
        p0.drawRect(rectangle,paint)
        invalidateSelf()

    }

    override fun setAlpha(p0: Int) {

    }

    override fun setColorFilter(p0: ColorFilter?) {

    }

    override fun getOpacity(): Int =
        PixelFormat.OPAQUE


}