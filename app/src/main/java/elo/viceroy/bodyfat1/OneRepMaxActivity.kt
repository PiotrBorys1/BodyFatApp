package elo.viceroy.bodyfat1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Bundle
import android.text.Editable
import android.util.Size
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.droidsonroids.gif.GifImageView
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt

class OneRepMaxActivity:AppCompatActivity() {
    val weightUnit_array=arrayOf("kg","lbs")
    lateinit var adapter_weight_unit:ArrayAdapter<Any>
    val exercises= mutableListOf("Bench press","Squat","Deadlift","Barbell row","Overhead Press","Biceps curl")
    val exercisesResourcesIds= mutableListOf(R.drawable.bench_press,R.drawable.squat,R.drawable.deadlift,R.drawable.barbell_row,R.drawable.overhead_press,
    R.drawable.biceps_curl)
    lateinit var exercisesgraphics:Bitmap
    val listofelements=MutableList<elements?>(exercises.size){elm->null}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_rep_max)
        val recyclerOneRepMax=findViewById<RecyclerView>(R.id.reyclerOneRep)
        val gifWait=findViewById<GifImageView>(R.id.gifImageViewWait)

        adapter_weight_unit = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return weightUnit_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@OneRepMaxActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=weightUnit_array[position]+ "  "
                val drawable = ContextCompat.getDrawable(context!!,R.drawable.arrowderty)?.apply {
                    setBounds(0, 0, 50, 50)
                }
                textView.setCompoundDrawables(null, null,drawable,null)
                return view
            }
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view=this@OneRepMaxActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=weightUnit_array[position]
                return view
            }
        }






        Thread{
            var i=0
            while (i<exercises.size)
            {
                val btmp=BitmapFactory.decodeResource(
                    this.resources,
                    exercisesResourcesIds[i]
                )
                val metrics=this.windowManager.currentWindowMetrics
                val windowinsets=metrics.windowInsets
                val insets = windowinsets.getInsetsIgnoringVisibility(android.view.WindowInsets.Type.navigationBars() or  android.view.WindowInsets.Type.displayCutout())
                val insetsWidth = insets.right + insets.left;
                val insetsHeight = insets.top + insets.bottom;

                // Legacy size that Display#getSize reports
                val bounds = metrics.getBounds();
                val legacySize = Size(bounds.width() - insetsWidth,
                bounds.height() - insetsHeight)
                val screenlowervalue= min(legacySize.width,legacySize.height)

              if (btmp.width>btmp.height)
              {
                    exercisesgraphics= ThumbnailUtils.extractThumbnail(
                        btmp,
                        screenlowervalue-TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10f,this.resources.displayMetrics).toInt(),
                        ((btmp.height.toFloat()/btmp.width.toFloat())*screenlowervalue.toFloat()).toInt()
                    )
              }
                else
              {
                  exercisesgraphics= ThumbnailUtils.extractThumbnail(
                      btmp,
                      ((btmp.width.toFloat()/btmp.height.toFloat())*screenlowervalue.toFloat()).toInt(),
                      screenlowervalue
                  )
              }



                listofelements[i]= elements(exercises[i],exercisesgraphics)
                i++
            }
            runOnUiThread{
                recyclerOneRepMax.layoutManager=LinearLayoutManager(this)
                recyclerOneRepMax.adapter=adapteroneRepMax(listofelements,this@OneRepMaxActivity)
                gifWait.isVisible=false
            }
        }.start()

    }
}