package elo.viceroy.bodyfat1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView

class chest_and_stomach_line_view@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):androidx.appcompat.widget.AppCompatImageView(context,attrs,defStyleAttr) {
    init{
        isClickable = true
        isScrollContainer=true

    }
    override fun performClick(): Boolean {
        return super.performClick()
    }
    val paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=Color.BLACK
        style=Paint.Style.FILL
        textSize=60f
        typeface=Typeface.SERIF
    }
    val paint_rect_line= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color=Color.RED
        style=Paint.Style.FILL

    }
    var rect_chest_middle_x=0f
    var rect_chest_middle_y=0f
    var rect_stoamch_middle_x=0f
    var rect_stoamch_middle_y=0f
    val line_thickness_2=15f
    val line_length_2=180f
    val path= Path()
    var text_line_chest="Chest"

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (rect_chest_middle_x==-10f && rect_chest_middle_y==-10f)
        {

        }
        else {
            if (rect_chest_middle_x < line_thickness_2) {
                rect_chest_middle_x = line_thickness_2
            } else if (rect_chest_middle_x > (width - line_thickness_2)) {
                rect_chest_middle_x = width - line_thickness_2
            }
            if (rect_chest_middle_y < line_length_2) {
                rect_chest_middle_y = line_length_2
            } else if (rect_chest_middle_y > height - line_length_2) {
                rect_chest_middle_y = height - line_length_2
            }
        }
        if (rect_stoamch_middle_x==-10f && rect_stoamch_middle_y==-10f)
        {

        }
        else {
            if (rect_stoamch_middle_x < line_thickness_2) {
                rect_stoamch_middle_x = line_thickness_2
            } else if (rect_stoamch_middle_x > (width - line_thickness_2)) {
                rect_stoamch_middle_x = width - line_thickness_2
            }
            if (rect_stoamch_middle_y < line_length_2) {
                rect_stoamch_middle_y = line_length_2
            } else if (rect_stoamch_middle_y > height - line_length_2) {
                rect_stoamch_middle_y = height - line_length_2
            }
        }
        if (canvas!=null) {

            if (rect_chest_middle_x<0f && rect_chest_middle_y<0f)
            {

            }
            else {
                canvas.drawRect(rect_chest_middle_x - line_thickness_2,rect_chest_middle_y-line_length_2,rect_chest_middle_x + line_thickness_2,rect_chest_middle_y+line_length_2,paint_rect_line)
                path.reset()
                path.moveTo(rect_chest_middle_x + line_thickness_2,rect_chest_middle_y-line_length_2)
                path.lineTo(rect_chest_middle_x + line_thickness_2,rect_chest_middle_y+line_length_2)
                canvas.drawTextOnPath(text_line_chest,path,0f,0f,paint)

            }
            if (rect_stoamch_middle_x<0f && rect_stoamch_middle_y<0f)
            {

            }
            else
            {
                canvas.drawRect(rect_stoamch_middle_x - line_thickness_2,rect_stoamch_middle_y-line_length_2,rect_stoamch_middle_x + line_thickness_2,rect_stoamch_middle_y+line_length_2,paint_rect_line)
                path.reset()
                path.moveTo(rect_stoamch_middle_x + line_thickness_2,rect_stoamch_middle_y-line_length_2)
                path.lineTo(rect_stoamch_middle_x + line_thickness_2,rect_stoamch_middle_x+line_length_2)
                canvas.drawTextOnPath("Stomach",path,0f,0f,paint)
            }
        }
    }
}