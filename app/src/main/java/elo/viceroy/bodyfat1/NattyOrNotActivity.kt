package elo.viceroy.bodyfat1

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.*
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import java.io.ByteArrayOutputStream
import kotlin.math.absoluteValue
import kotlin.math.pow

class NattyOrNotActivity : AppCompatActivity()  {

lateinit var photoBitmap:Bitmap
    lateinit var bitmapToSet:Bitmap
    var photo_cut_number=0
    var coef_stomach_to_shoulder=0f
    lateinit var photoBitmapcutted:Bitmap
    val secondactivit=SecondActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        setContentView(R.layout.natty_or_not)
        val tekst12345=findViewById<TextView>(R.id.tekstview12345)
        val rangeview=findViewById<ShouldersAndWaistView>(R.id.shouldandwaist)
        rangeview.isVisible=false
        rangeview.text="Shoulders"
        val rangeviewwaist=findViewById<ShouldersAndWaistView>(R.id.shouldandwaist2)
        rangeviewwaist.isVisible=false
        rangeviewwaist.text="Waist"
        val imagephoto=findViewById<ImageView>(R.id.photo_natty)
        imagephoto.scaleType=ImageView.ScaleType.FIT_XY
        var buttonOK=findViewById<Button>(R.id.buttonOK1)
        val frameviewtop=findViewById<FrameView>(R.id.top_frame1)
        frameviewtop.istop=true
        val frameviewbottom=findViewById<FrameView>(R.id.bottom_frame1)
        frameviewbottom.isbottom=true
        val frameviewleft=findViewById<FrameView1>(R.id.left_frame1)
        frameviewleft.isleft=true
        val frameviewright=findViewById<FrameView1>(R.id.right_frame1)
        frameviewright.isright=true
        val layout=findViewById<ConstraintLayout>(R.id.layoutnatty)
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


        frameviewbottom.setOnTouchListener(bottom_scroll)
        frameviewleft.setOnTouchListener(left_scroll)
        frameviewright.setOnTouchListener(right_scroll)
        frameviewtop.setOnTouchListener(top_scroll)

        var handler = object: Handler(Looper.getMainLooper())
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what==1)
                {
                    val FFMI=(((100.0-secondactivit.body_fat)/100.0)*intent.getDoubleExtra("weight",0.0))/
                            ( (intent.getDoubleExtra("height",0.0))/100.0).pow(2)
                    val normalizedFFMI=FFMI+6.1*(1.8-( (intent.getDoubleExtra("height",0.0))/100.0)).absoluteValue
                    var FFMI_to_degrees:Double

                    if (normalizedFFMI>40.0)
                    {
                        FFMI_to_degrees=180.0
                    }
                    else if (normalizedFFMI<12.0)
                    {
                        FFMI_to_degrees=0.0
                    }
                    else
                    { if (intent.getStringExtra("gender") == "Male")
                        {
                        if (secondactivit.body_fat<13.5) {
                            FFMI_to_degrees = ((normalizedFFMI - 13.0 )/ 12.0) * 90.0
                            }
                            else
                            {
                            FFMI_to_degrees = ((normalizedFFMI - 13.0 )/ 12.0) * 90.0
                            }
                        }
                        else
                        {
                            FFMI_to_degrees = ((normalizedFFMI -10.0)/ 12.0) * 90.0
                        }

                    }

                    /*tekst12345.text=coef_stomach_to_shoulder.toString()+"\n"+secondactivit.body_fat.toString()*/

               /*coef_stomach_to_shoulder.toString()+"\n"+FFMI_to_degrees.toString()*//*secondactivit.body_fat.toString()+"\n"+normalizedFFMI.toString()+"\n"+( (intent.getDoubleExtra("height",0.0))/100.0).toString()*/
                    val inte=Intent(this@NattyOrNotActivity,NaturalActivity::class.java)
                    inte.putExtra("NattyPrecent",FFMI_to_degrees).putExtra("BF",secondactivit.body_fat).putExtra("FFMI",normalizedFFMI)
                    startActivity(inte)
                }

            }
        }


        buttonOK.setOnClickListener {
             if (photo_cut_number==0)
             {
                 photoBitmapcutted = Bitmap.createBitmap(
                     bitmapToSet,
                     (((frameviewleft.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet.width.toDouble()).toInt(),
                     (((frameviewtop.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet.height.toDouble()).toInt(),
                     (bitmapToSet.width.toDouble() - ((frameviewright.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet.width.toDouble()).toInt() -
                             (((frameviewleft.photosize.toDouble() + 10) / layout.width.toDouble()) * bitmapToSet.width.toDouble()).toInt(),
                     (bitmapToSet.height.toDouble() - ((frameviewbottom.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet.height.toDouble()).toInt() -
                             (((frameviewtop.photosize.toDouble() + 10) / layout.height.toDouble()) * bitmapToSet.height.toDouble()).toInt()

                 )
                 photo_cut_number+=1
                 frameviewbottom.isVisible=false
                 frameviewleft.isVisible=false
                 frameviewright.isVisible=false
                 frameviewtop.isVisible=false
                 rangeview.isVisible=true
                 rangeviewwaist.isVisible=true
             }
            else if (photo_cut_number==1)
             {


                if (intent.getStringExtra("gender") == "Male") {
                    coef_stomach_to_shoulder =
                        (rangeviewwaist.rangewidth - rangeview.rangewidth) / rangeview.rangewidth + 0.47f

                }
                 else
                {
                    coef_stomach_to_shoulder =
                        (rangeviewwaist.rangewidth - rangeview.rangewidth) / rangeview.rangewidth + 0.2f

                }
                 if (intent.getStringExtra("gender") == "Male") {


                     calc9(
                         secondactivit,
                         photoBitmapcutted,
                         handler,
                         intent,
                         500,
                         500,
                         true,
                         coef_stomach_to_shoulder
                     ).start()
                 } else if (intent.getStringExtra("gender") == "Female") {

                     calc9(
                         secondactivit,
                         photoBitmapcutted,
                         handler,
                         intent,
                         500,
                         500,
                         false,
                         coef_stomach_to_shoulder
                     ).start()


                 }

             }

        }



        Thread {

            val inptstr = applicationContext.contentResolver.openInputStream(intent.data!!)
            val options=BitmapFactory.Options()
            photoBitmap = BitmapFactory.decodeStream(inptstr, null, options)!!
            inptstr?.close()
            val matrix=Matrix()
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

            bitmapToSet=Bitmap.createBitmap(photoBitmap,0,0,photoBitmap.width,photoBitmap.height,matrix,true)
            val byteArrayoutStream= ByteArrayOutputStream()
            bitmapToSet.compress(Bitmap.CompressFormat.JPEG,60,byteArrayoutStream)
            bitmapToSet=BitmapFactory.decodeByteArray(
                byteArrayoutStream.toByteArray(),
                0,
                byteArrayoutStream.size()
            )
            byteArrayoutStream.close()
            runOnUiThread{
                imagephoto.setImageBitmap(bitmapToSet)
            }
        }.start()

        val move_view=object: View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                var index=p1?.actionIndex


                    var xx = p1!!.getX(index!!)
                    var yy = p1!!.getY(index!!)

                    if (xx> rangeview.widthstart+rangeview.sidethickness && xx<rangeview.widthstart+rangeview.sidethickness+rangeview.rangewidth
                        && yy>rangeview.heightstart && yy<rangeview.heightstart+rangeview.rangeheight) {
                        if (xx>rangeview.rangewidth/2+rangeview.sidethickness && xx<rangeview.viewwidth-(rangeview.rangewidth/2+rangeview.sidethickness)) {
                            rangeview.widthstart =
                                xx - (rangeview.sidethickness + rangeview.rangewidth / 2)
                        }

                        if(yy>rangeview.rangeheight/2 && yy<rangeview.viewheight-rangeview.rangeheight/2) {
                            rangeview.heightstart = yy - rangeview.rangeheight / 2
                        }
                    }
                    else
                    {  if (yy>rangeview.heightstart && yy<rangeview.heightstart+rangeview.rangeheight) {
                        if (xx < rangeview.widthstart + rangeview.sidethickness + rangeview.rangewidth / 2) {
                            rangeview.rangewidth = rangeview.rangewidth + rangeview.widthstart - xx
                            rangeview.widthstart = xx

                        } else {
                            rangeview.rangewidth =
                                xx - rangeview.widthstart - 2 * rangeview.sidethickness
                        }
                    }
                    }
                    rangeview.invalidate()



                return true
            }
        }

        val move_view_1=object: View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                var index=p1?.actionIndex


                var xx = p1!!.getX(index!!)
                var yy = p1!!.getY(index!!)

                if (xx> rangeviewwaist.widthstart+rangeviewwaist.sidethickness && xx<rangeviewwaist.widthstart+rangeviewwaist.rangewidth+rangeviewwaist.sidethickness
                    && yy>rangeviewwaist.heightstart && yy<rangeviewwaist.heightstart+rangeviewwaist.rangeheight) {
                    if (xx>rangeviewwaist.rangewidth/2+rangeviewwaist.sidethickness && xx<rangeviewwaist.viewwidth-(rangeviewwaist.rangewidth/2+rangeview.sidethickness)) {
                        rangeviewwaist.widthstart =
                            xx - (rangeviewwaist.sidethickness + rangeviewwaist.rangewidth / 2)
                    }

                    if(yy>rangeviewwaist.rangeheight/2 && yy<rangeviewwaist.viewheight-rangeviewwaist.rangeheight/2) {
                        rangeviewwaist.heightstart = yy - rangeviewwaist.rangeheight / 2
                    }
                }
                else
                {
                    if (yy>rangeviewwaist.heightstart && yy<rangeviewwaist.heightstart+rangeviewwaist.rangeheight) {
                        if (xx < rangeviewwaist.widthstart + rangeviewwaist.sidethickness + rangeviewwaist.rangewidth / 2) {
                            rangeviewwaist.rangewidth =
                                rangeviewwaist.rangewidth + rangeviewwaist.widthstart - xx
                            rangeviewwaist.widthstart = xx

                        } else {
                            rangeviewwaist.rangewidth =
                                xx - rangeviewwaist.widthstart - 2 * rangeviewwaist.sidethickness
                        }
                    }
                }
                rangeviewwaist.invalidate()



                return true
            }
        }

        rangeview.setOnTouchListener(move_view)
        rangeviewwaist.setOnTouchListener(move_view_1)


    }
}