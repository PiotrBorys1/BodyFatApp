package elo.viceroy.bodyfat1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Binder
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.math.roundToInt

class NaturalActivity : AppCompatActivity()  {

    var nattydegree=0.0
    var matrix= Matrix()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.natural_activity)
        val natty_clock=findViewById<NattyClockView>(R.id.clockView)
        val text_verdict=findViewById<TextView>(R.id.text_verdict)
        val text_estimatedvalues=findViewById<TextView>(R.id.text_estimatedvalues)
        var needle_bitmap=BitmapFactory.decodeResource(resources,R.drawable.clock_needle)
        var clock_bitmap=BitmapFactory.decodeResource(resources,R.drawable.clock_scale)
        val animation= AnimationUtils.loadAnimation(this,R.anim.from_small_to_big)
        natty_clock.setImageBitmap(clock_bitmap)
        natty_clock.bitmap_needle=needle_bitmap
        natty_clock.invalidate()
        nattydegree =intent.getDoubleExtra("NattyPrecent",0.0)
        val number_samples= ((nattydegree/180.0)*90.0).toInt()
        var natty_sample=nattydegree/number_samples.toDouble()
       Thread{
            var i=0
            while (i<number_samples)
            {
                natty_clock.rotate_degres=natty_sample.toFloat()*i.toFloat()
                runOnUiThread{
                    natty_clock.invalidate()
                }

            Thread.sleep(50)
                i++

            }
            runOnUiThread {
                if(nattydegree<90.0) {
                    text_verdict.setTextColor(Color.GREEN)
                    text_verdict.text = "Natural"
                }
                else if (nattydegree>=90.0 && nattydegree<=110.0)
                {
                    text_verdict.setTextColor(Color.YELLOW)
                    text_verdict.text = "Suspicious"
                }
                else
                {
                    text_verdict.setTextColor(Color.RED)
                    text_verdict.text = "Not Natural"
                }



                val bftext=(((intent.getDoubleExtra("BF",0.0)* 10.0).roundToInt()) / 10.0).toString()
                val ffmitext=(((intent.getDoubleExtra("FFMI",0.0)* 10.0).roundToInt()) / 10.0).toString()
                val textffmiunit="Estimated:\n\nBody Fat= "+bftext+" %\n\n"+
                        "FFMI= "+ffmitext+" "+ "kg/m2"

                val mStringSpan = SpannableStringBuilder(textffmiunit)
                mStringSpan.setSpan(SuperscriptSpan(), 31+bftext.length+ffmitext.length, 33+bftext.length+ffmitext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                mStringSpan.setSpan(SubscriptSpan(), 34+bftext.length+ffmitext.length, 35+bftext.length+ffmitext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                mStringSpan.setSpan(RelativeSizeSpan(0.7f), 31+bftext.length+ffmitext.length, 33+bftext.length+ffmitext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                mStringSpan.setSpan(RelativeSizeSpan(0.7f), 34+bftext.length+ffmitext.length, 35+bftext.length+ffmitext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                mStringSpan.setSpan(RelativeSizeSpan(0.5f),35+bftext.length+ffmitext.length, mStringSpan.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                text_estimatedvalues.text=mStringSpan
                text_verdict.startAnimation(animation)
                text_estimatedvalues.startAnimation(animation)
            }
        }.start()


    }
}