package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.*
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifImageView
import java.io.ByteArrayOutputStream
import java.lang.Thread.sleep
import kotlin.math.roundToInt

class SecondActivity: AppCompatActivity()  {
    lateinit var photoBitmap :Bitmap
    lateinit var photoBitmap_side :Bitmap
    lateinit var photoBitmapcutted:Bitmap
    lateinit var photoBitmapcutted_side:Bitmap
    var tobtmbottom:Boolean=false
    var body_fat=0.0
    var i:Int=0
    var j:Int=0
    var max_harm=0
    var dar_to_light_ixels=0.0
    var gender_er=""
    var natty_percent=80.0
    lateinit var bitmapToSet:Bitmap
    lateinit var bitmapToSet_side:Bitmap
    lateinit var bitmp:Bitmap
    lateinit var bitmpframecheckedwidth:Bitmap
    lateinit var bitmpframecheckedheight:Bitmap
    lateinit var bitmptop:Bitmap
    lateinit var bitmpbottom:Bitmap
    lateinit var bitmpright:Bitmap
    lateinit var bitmpleft:Bitmap
    var two_photos_truncated=0
var reflection_start=false
    var once_clicked=false
    var coef_stomach_to_chest=0f
    var viewTouchedtop:Boolean=false
    var viewTouchedbottom:Boolean=false
    var viewTouchedright:Boolean=false
    var viewTouchedleft:Boolean=false
            @RequiresApi(Build.VERSION_CODES.TIRAMISU)
            @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val image=findViewById<ImageView>(R.id.photo)
        val image_top=findViewById<ImageView>(R.id.phototop)
        val image_bottom=findViewById<ImageView>(R.id.photobottom)
        val tekst=findViewById<TextView>(R.id.tekst1)
        val imagegif=findViewById<GifImageView>(R.id.gifImageView)
        val layout=findViewById<ConstraintLayout>(R.id.layoutSecondActivity)
        val frameviewtop=findViewById<FrameView>(R.id.top_frame)
                frameviewtop.istop=true
        val frameviewbottom=findViewById<FrameView>(R.id.bottom_frame)
                frameviewbottom.isbottom=true
        val frameviewleft=findViewById<FrameView1>(R.id.left_frame)
                frameviewleft.isleft=true
        val frameviewright=findViewById<FrameView1>(R.id.right_frame)
                frameviewright.isright=true
        val line_for_chest=findViewById<chest_and_stomach_line_view>(R.id.line_for_chest)
                line_for_chest.isVisible=false
         val line_for_stomach=findViewById<chest_and_stomach_line_view>(R.id.line_for_stomach)
                line_for_stomach.isVisible=false
                val mirror_button=findViewById<Button>(R.id.mirror)
                mirror_button.isVisible=false
        val buttoncacl=findViewById<Button>(R.id.buttonstart)
        val button_check_natty=findViewById<Button>(R.id.check_naturality)
                button_check_natty.isVisible=false
        image.scaleType=ImageView.ScaleType.FIT_XY
                image_top.scaleType=ImageView.ScaleType.FIT_XY
                image_bottom.scaleType=ImageView.ScaleType.FIT_XY
               /* image.setImageBitmap(BitmapFactory.decodeResource(resources,R.drawable.icon3))*/
               /* frameviewtopchecked.scaleType=ImageView.ScaleType.FIT_XY
                frameviewbottomchecked.scaleType=ImageView.ScaleType.FIT_XY
                frameviewleftchecked.scaleType=ImageView.ScaleType.FIT_XY
                frameviewrightchecked.scaleType=ImageView.ScaleType.FIT_XY*/
                imagegif.isVisible=false
                val set_Chest_line_touch_listener= object: View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                        var index=p1?.actionIndex

                        if ( p1!=null) {
                            line_for_chest.rect_chest_middle_x=p1.getX(index!!)
                            line_for_chest.rect_chest_middle_y=p1.getY(index)
                            line_for_chest.invalidate()
                        }
                        return true
                    }
                }


                val set_stomach_line_touch_listener= object: View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                        var index=p1?.actionIndex

                        if ( p1!=null) {
                            line_for_stomach.rect_stoamch_middle_x=p1.getX(index!!)
                            line_for_stomach.rect_stoamch_middle_y=p1.getY(index)
                            line_for_stomach.invalidate()
                        }
                        return true
                    }
                }

                val top_scroll=object: View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                        var index=p1?.actionIndex

                        if ( p1!=null) {
                        if (p0 is FrameView)
                            {
                            if ((p1.getY(index!!)).toInt()<layout.height-frameviewbottom.photosize-20 && (p1.getY(index!!)).toInt()>10 ) {
                                p0.photosize = p1.getY(index!!).toInt()
                                p0.requestLayout()
                            }
                                frameviewleft.top_start_of_thin_frame=p0.photosize
                                frameviewright.top_start_of_thin_frame=p0.photosize
                                frameviewleft.requestLayout()
                                frameviewright.requestLayout()
                            }
                        }
                        return true
                    }
                }

                val bottom_scroll=object: View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                        var index=p1?.actionIndex

                        if ( p1!=null) {
                            if (p0 is FrameView)
                            {
                                if (p0.photosize+frameviewtop.photosize<=layout.height-20 && p0.photosize>=40) {
                                    p0.photosize = p0.photosize-p1.getY(index!!).toInt()

                                }
                                else if (p0.photosize<40 && p1.getY(index!!)>0 )
                                {
                                    p0.photosize=35

                                }
                                else if (p0.photosize+frameviewtop.photosize>layout.height-20 && p1.getY(index!!)<0)
                                {
                                    p0.photosize=layout.height-frameviewtop.photosize-18

                                }
                                else if (p1.getY(index!!)<0 && p0.photosize<40)
                                {
                                    p0.photosize = p0.photosize-p1.getY(index!!).toInt()
                                }
                                else if (p0.photosize+frameviewtop.photosize>layout.height-20 && p1.getY(index!!)>0 )
                                {
                                    p0.photosize = p0.photosize-p1.getY(index!!).toInt()
                                }
                                p0.requestLayout()
                                frameviewleft.bottom_end_of_thin_frame=p0.photosize
                                frameviewright.bottom_end_of_thin_frame=p0.photosize
                                frameviewleft.requestLayout()
                                frameviewright.requestLayout()
                            }

                        }
                        return true
                    }
                }

                val left_scroll=object: View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                        var index=p1?.actionIndex

                        if ( p1!=null) {
                            if (p0 is FrameView1)
                            {  if((p1.getX(index!!)).toInt()<(layout.width-frameviewright.photosize-20) && (p1.getX(index!!)).toInt()>10) {
                                p0.photosize = p1.getX(index!!).toInt()
                                p0.requestLayout()
                            }
                                frameviewtop.left_start_of_thin_frame=p0.photosize
                                frameviewbottom.left_start_of_thin_frame=p0.photosize
                                frameviewtop.requestLayout()
                                frameviewbottom.requestLayout()
                            }
                        }
                        return true
                    }
                }

                val right_scroll=object: View.OnTouchListener{
                    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                        var index=p1?.actionIndex

                        if ( p1!=null) {
                            if (p0 is FrameView1)
                            {   if (p0.photosize+frameviewleft.photosize<=layout.width-20 && p0.photosize>=40) {
                                p0.photosize = p0.photosize-p1.getX(index!!).toInt()

                            }
                                else if (p0.photosize<40 && p1.getX(index!!).toInt()>0)
                            {
                                p0.photosize=35
                            }
                                else if (p0.photosize+frameviewleft.photosize>layout.width-20 && p1.getX(index!!).toInt()<0 )
                            {
                                p0.photosize= layout.width-frameviewleft.photosize-18
                            }
                            else if (p0.photosize<40 && p1.getX(index!!).toInt()<0)
                            {
                                p0.photosize = p0.photosize-p1.getX(index!!).toInt()
                            }
                            else if (p0.photosize+frameviewleft.photosize>layout.width-20 && p1.getX(index!!).toInt()>0)
                            {
                                p0.photosize = p0.photosize-p1.getX(index!!).toInt()
                            }
                                p0.requestLayout()
                                frameviewtop.right_end_of_thin_frame=p0.photosize
                                frameviewbottom.right_end_of_thin_frame=p0.photosize
                                frameviewtop.requestLayout()
                                frameviewbottom.requestLayout()
                            }
                        }
                        return true
                    }
                }

        var handler = object: Handler(Looper.getMainLooper())
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                imagegif.isVisible=false
                if (body_fat==-1.0)
                {
                    tekst.text =
                        "Not a human"
                }
                else {
                    tekst.text =/*max_harm.toString()+" "+dar_to_light_ixels.toString()*/
                "BF:" + " " + (((body_fat * 10.0).roundToInt()) / 10.0).toString()+" "+"%"
                    /*button_check_natty.isVisible=true*/

}

}
}
/*button_check_natty.setOnClickListener {
    val intent_chck_nat=Intent(this,NaturalActivity::class.java )
    intent_chck_nat.putExtra("NattyPrecent",natty_percent)
    startActivity(intent_chck_nat)

}*/
mirror_button.setOnClickListener {

imagegif.isVisible=true
if (!reflection_start) {
 if (!once_clicked) {
     once_clicked=true
     Thread {
         reflection_start = true
         var mirror_reflection_photo_array =
             IntArray((bitmapToSet_side.height/2) * (bitmapToSet_side.width/2))
         var i = 0
         var j = 0
         while (i < bitmapToSet_side.height/2) {
             while (j < bitmapToSet_side.width/2) {
                 mirror_reflection_photo_array[i * (bitmapToSet_side.width/2) + j] =
                     bitmapToSet_side.getColor(
                         (bitmapToSet_side.width - 1) - 2*j,
                         2*i
                     )
                         .toArgb()
                 j++
             }
             j = 0
             i++
         }
         val config: Bitmap.Config = bitmapToSet_side.config
         val reflected_bitmap = Bitmap.createBitmap(
             mirror_reflection_photo_array,
             bitmapToSet_side.width/2,
             bitmapToSet_side.height/2,
             config
         )

         runOnUiThread {
             imagegif.isVisible = false
             bitmapToSet_side = reflected_bitmap
             image.setImageBitmap(bitmapToSet_side)
             reflection_start = false
         }
     }.start()
 }
 else
 {
     Thread {
     reflection_start = true
     var mirror_reflection_photo_array =
         IntArray(bitmapToSet_side.height * bitmapToSet_side.width)
     var i = 0
     var j = 0
     while (i < bitmapToSet_side.height) {
         while (j < bitmapToSet_side.width) {
             mirror_reflection_photo_array[i * bitmapToSet_side.width + j] =
                 bitmapToSet_side.getColor(
                     (bitmapToSet_side.width - 1) - j,
                     i
                 )
                     .toArgb()
             j++
         }
         j = 0
         i++
     }
     val config: Bitmap.Config = bitmapToSet_side.config
     val reflected_bitmap = Bitmap.createBitmap(
         mirror_reflection_photo_array,
         bitmapToSet_side.width,
         bitmapToSet_side.height,
         config
     )

     runOnUiThread {
         imagegif.isVisible = false
         bitmapToSet_side = reflected_bitmap
         image.setImageBitmap(bitmapToSet_side)
         reflection_start = false
     }
 }.start()

 }
}
}

var handler1 = object: Handler(Looper.getMainLooper())
{
@RequiresApi(Build.VERSION_CODES.Q)
override fun handleMessage(msg: Message) {
 super.handleMessage(msg)

 frameviewbottom.setOnTouchListener(bottom_scroll)
 frameviewleft.setOnTouchListener(left_scroll)
 frameviewright.setOnTouchListener(right_scroll)
 frameviewtop.setOnTouchListener(top_scroll)

 buttoncacl.setOnClickListener {
     if (two_photos_truncated==0) {

         photoBitmapcutted = Bitmap.createBitmap(
             bitmapToSet,
             (((frameviewleft.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet.width.toDouble()).toInt(),
             (((frameviewtop.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet.height.toDouble()).toInt(),
             (bitmapToSet.width.toDouble() - ((frameviewright.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet.width.toDouble()).toInt() -
                     (((frameviewleft.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet.width.toDouble()).toInt(),
             (bitmapToSet.height.toDouble() - ((frameviewbottom.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet.height.toDouble()).toInt() -
                     (((frameviewtop.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet.height.toDouble()).toInt()

         )

         image.setImageBitmap(bitmapToSet_side)
         mirror_button.isVisible=true
         two_photos_truncated=1


     }
     else if (two_photos_truncated==1)
     {
         mirror_button.isVisible=false
         photoBitmapcutted_side = Bitmap.createBitmap(
             bitmapToSet_side,
             (((frameviewleft.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet_side.width.toDouble()).toInt(),
             (((frameviewtop.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet_side.height.toDouble()).toInt(),
             (bitmapToSet_side.width.toDouble() - ((frameviewright.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet_side.width.toDouble()).toInt() -
                     (((frameviewleft.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet_side.width.toDouble()).toInt(),
             (bitmapToSet_side.height.toDouble() - ((frameviewbottom.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet_side.height.toDouble()).toInt() -
                     (((frameviewtop.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet_side.height.toDouble()).toInt()

         )

         image.setImageBitmap(photoBitmapcutted_side)
         frameviewbottom.isVisible=false
         frameviewleft.isVisible=false
         frameviewright.isVisible=false
         frameviewtop.isVisible=false
         line_for_chest.rect_stoamch_middle_x=-10f
         line_for_chest.rect_stoamch_middle_y=-10f
         line_for_stomach.rect_chest_middle_x=-10f
         line_for_stomach.rect_chest_middle_y=-10f
         if (intent.getStringExtra("gender") == "Female")
         {
             line_for_chest.text_line_chest="Breast"
         }
         line_for_chest.isVisible=true
         line_for_stomach.isVisible=true
         line_for_chest.setOnTouchListener(set_Chest_line_touch_listener)
         line_for_stomach.setOnTouchListener(set_stomach_line_touch_listener)
         two_photos_truncated=2
     }
     else {

         line_for_chest.isVisible=false
         line_for_stomach.isVisible=false
         coef_stomach_to_chest = ((line_for_stomach.rect_stoamch_middle_x-line_for_stomach.line_thickness_2)-(line_for_chest.rect_chest_middle_x-line_for_chest.line_thickness_2))/(line_for_stomach.width).toFloat()
         imagegif.isVisible = true
         it.isVisible=false
         image.setImageBitmap(null)
         /*val prop_height =
             photoBitmapcutted.height.toFloat() / 500f
         val prop_width =
             photoBitmapcutted.width.toFloat() / 500f
         var bitmapek=Bitmap.createBitmap(photoBitmapcutted,(20*prop_width).toInt(),(0*prop_height).toInt(),
             (450*prop_width).toInt(),(70*prop_height).toInt())*/
         image_top.setImageBitmap(bitmapToSet)


         GlobalScope.launch {
             if (intent.getStringExtra("gender") == "Male") {


                 calc9(
                     this@SecondActivity,
                     photoBitmapcutted,
                     handler,
                     intent,
                     500,
                     500,
                     true,
                     coef_stomach_to_chest
                 ).start()
             } else if (intent.getStringExtra("gender") == "Female") {

                 calc9(
                     this@SecondActivity,
                     photoBitmapcutted,
                     handler,
                     intent,
                     500,
                     500,
                     false,
                     coef_stomach_to_chest
                 ).start()


             }
         }
     }
 }
}
}




Thread {

val inptstr = applicationContext.contentResolver.openInputStream(intent.data!!)
val inptstr_side = applicationContext.contentResolver.openInputStream(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,intent.getLongExtra("side_photo_uri_index",0)))
/* val inptstr_side = applicationContext.contentResolver.openInputStream(intent.getParcelableExtra("side_photo_uri",Uri::class.java)!!)*/
val options=BitmapFactory.Options()

photoBitmap = BitmapFactory.decodeStream(inptstr, null, options)!!
photoBitmap_side=BitmapFactory.decodeStream(inptstr_side, null, options)!!

inptstr?.close()
inptstr_side?.close()

val matrix=Matrix()
val matrix_side=Matrix()
if (intent.getIntExtra("orientation",0)==90) {
matrix.postRotate(90f)
}
else if (intent.getIntExtra("orientation",0)==180)
{
matrix.postRotate(180f)
}
else if (intent.getIntExtra("orientation",0)==270)
{
matrix.postRotate(270f)
}

if (intent.getIntExtra("orientation_side",0)==90) {
matrix_side.postRotate(90f)
}
else if (intent.getIntExtra("orientation_side",0)==180)
{
matrix_side.postRotate(180f)
}
else if (intent.getIntExtra("orientation_side",0)==270)
{
matrix_side.postRotate(270f)
}
bitmapToSet=Bitmap.createBitmap(photoBitmap,0,0,photoBitmap.width,photoBitmap.height,matrix,true)
bitmapToSet_side=Bitmap.createBitmap(photoBitmap_side,0,0,photoBitmap_side.width,photoBitmap_side.height,matrix_side,true)
val byteArrayoutStream= ByteArrayOutputStream()
val byteArrayoutStream_side= ByteArrayOutputStream()
bitmapToSet.compress(Bitmap.CompressFormat.JPEG,40,byteArrayoutStream)
bitmapToSet_side.compress(Bitmap.CompressFormat.JPEG,40,byteArrayoutStream_side)
bitmapToSet=BitmapFactory.decodeByteArray(
byteArrayoutStream.toByteArray(),
0,
byteArrayoutStream.size()
)
byteArrayoutStream.reset()
bitmapToSet_side=BitmapFactory.decodeByteArray(
byteArrayoutStream_side.toByteArray(),
0,
byteArrayoutStream_side.size()
)
byteArrayoutStream_side.reset()
sleep(10)
runOnUiThread {
image.setImageBitmap(bitmapToSet)
}

val mess=Message.obtain(handler1,1,1,1)
handler1.sendMessage(mess)

}.start()
}

override fun onBackPressed() {
finish()
}

}