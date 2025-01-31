package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ThumbnailUtils
import android.os.*
import android.text.Editable
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

class BmiActivity:AppCompatActivity() {
    val gender_array=arrayOf("Male","Female")
    var listofelemrntspopup= mutableListOf<elements>()
    var gender=gender_array[0]
    var BMI_value=0.0
    var weight1=0.0
    var height1=0.0
    var age=0.0
    var height_uniit=0
    var weight_uniit=0
    val heightUnit_array=arrayOf("cm","inch")
    val weightUnit_array=arrayOf("kg","lbs")
    lateinit var handler1:Handler
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
setContentView(R.layout.bmi_activity)

val scale=findViewById<ScaleView>(R.id.scale_bmi)








        scale.isVisible=false
        scale.focusable=View.NOT_FOCUSABLE
        val  spinner_child_adult=findViewById<Spinner>(R.id.gender_BMI)
        val spinner_weight=findViewById<Spinner>(R.id.weightUnits1)
        val spinnerHeight=findViewById<Spinner>(R.id.height_unit1)
        var edit_text_weight=findViewById<EditText>(R.id.weight_to_put1)

        var edit_text_height=findViewById<EditText>(R.id.height_to_put1)
        val button_calculate=findViewById<Button>(R.id.Calculate1)
        var editText_age=findViewById<EditText>(R.id.age)
        var layout_BMI=findViewById<ConstraintLayout>(R.id.BMI_layout)

        /*pop up information about BMI*/
       requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        val screenwidth=windowManager.currentWindowMetrics.bounds.width()
        val screenheight=windowManager.currentWindowMetrics.bounds.height()
        var popupView=this.layoutInflater.inflate(R.layout.new_for_popup,null)
        val imagepaper=popupView.findViewById<ImageView>(R.id.imagepapaer)
        val recyclerscrollview=popupView.findViewById<HorizontalScrollView>(R.id.horizonscroll)


        val recyclerpopup=popupView.findViewById<RecyclerView>(R.id.recyclerpopup)
        var bmichart=ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(resources,R.drawable.bmichart),1200,675)
        var bmichild=ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(resources,R.drawable.bmichild),760,800)

        listofelemrntspopup.add(elements("BMI- Body Mass Index", null))
        listofelemrntspopup.add(elements("BMI calculator compute the person's leanness or corpulence\n based on their height and weight and indicate if value is in normal level.\n\n"+
                "BMI is calculated from equation:\nbody weight [kg]/ height^2 [cm]\n\n "+
                "Ranges for results are different for adults and children/teenagers. Look graphs below", bmichart))
        listofelemrntspopup.add(elements("               BMI chart for adults\n\n",bmichild))
        listofelemrntspopup.add(elements("               BMI chart for children\n\n",null))
        listofelemrntspopup.add(elements("The main limitation of BMI is not taking into account relation of body fat mass to muscle mass.\n" +
                "I do not recommend to use this calculator by people training at te gym. ",null))
        recyclerpopup.layoutManager=LinearLayoutManager(this)
        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this)
        imagepaper.scaleType=ImageView.ScaleType.FIT_XY
        val btmp=BitmapFactory.decodeResource(resources,R.drawable.popup1)

        /*val textup=findViewById<TextView>(R.id.tekstview1234)*/
       Thread {
           val intpstrm = resources.openRawResource(R.raw.popupphoto)
           val bytearray = intpstrm.readBytes()
           intpstrm.close()
           var intarrray =
               bytearray.decodeToString().split(";").toTypedArray().map { it.toInt() }.toIntArray()
           var btmp1=Bitmap.createBitmap(intarrray,500,500,btmp.config)
           var matrix=Matrix().also{block->block.postRotate(90f)}
           btmp1= Bitmap.createBitmap(btmp1,0,0,btmp1.width,btmp1.height,matrix,true)

           runOnUiThread{
           imagepaper.setImageBitmap(btmp1)
               }
       }.start()

        val popupbackground=popupView.findViewById<ConstraintLayout>(R.id.popupbackground1)
        val popuupWindow=PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val imageQuestion=findViewById<ImageView>(R.id.questionBMI)
        imageQuestion.setOnClickListener {
            requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LOCKED
            popuupWindow.isOutsideTouchable = true
            popuupWindow.isFocusable = true
            /*popuupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))*/
            popuupWindow.showAsDropDown(layout_BMI)

        }
        popupbackground.setOnClickListener {
            if (popuupWindow.isShowing)
            {
                popuupWindow.dismiss()
                requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
        val textviewtp=findViewById<TextView>(R.id.tekstview1234)
/*handler configuration changes*/
        handler1=object :Handler(Looper.getMainLooper())
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what==1)
                {
                    /*textviewtp.text=resources.configuration.orientation.toString()*/
                    if (resources.configuration.orientation==android.content.res.Configuration.ORIENTATION_LANDSCAPE)
                    {
                        imagepaper.layoutParams.width=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,650f,resources.displayMetrics).toInt()
                        imagepaper.layoutParams.height=ConstraintLayout.LayoutParams.MATCH_PARENT
                        imagepaper.invalidate()
                        recyclerscrollview.layoutParams.width=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,500f,resources.displayMetrics).toInt()
                        recyclerscrollview.layoutParams.height=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,260f,resources.displayMetrics).toInt()
                        recyclerpopup.layoutManager=LinearLayoutManager(this@BmiActivity)
                        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this@BmiActivity)
                        popupbackground.forceLayout()
                    }
                    else

                    {
                        imagepaper.layoutParams.width=ConstraintLayout.LayoutParams.MATCH_PARENT
                        imagepaper.layoutParams.height=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,500f,resources.displayMetrics).toInt()
                        imagepaper.invalidate()
                        recyclerscrollview.layoutParams.width=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300f,resources.displayMetrics).toInt()
                        recyclerscrollview.layoutParams.height=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,360f,resources.displayMetrics).toInt()
                        recyclerpopup.layoutManager=LinearLayoutManager(this@BmiActivity)
                        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this@BmiActivity)
                        popupbackground.forceLayout()

                    }
                }

            }
        }

        var blackLine=findViewById<blackLineView>(R.id.blackLine)
        blackLine.isVisible=false
        blackLine.focusable=View.NOT_FOCUSABLE


        val drawable_sp_icons = ContextCompat.getDrawable(this,R.drawable.weight_icon)?.apply {
            setBounds(0, 0, 300, 300)
        }
        textviewtp.setCompoundDrawables(null, drawable_sp_icons,null,null)

        val adapter_gender = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return gender_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BmiActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=gender_array[position]+ "  "
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
                val view=this@BmiActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=gender_array[position]
                return view
            }
        }
        val adapter_height_unit = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return heightUnit_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BmiActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=heightUnit_array[position]+ "  "
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
                val view=this@BmiActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=heightUnit_array[position]
                return view
            }
        }
        val adapter_weight_unit = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return weightUnit_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BmiActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
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
                val view=this@BmiActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=weightUnit_array[position]
                return view
            }
        }
        spinner_child_adult.adapter=adapter_gender
        spinner_weight.adapter=adapter_weight_unit
        spinnerHeight.adapter=adapter_height_unit

        spinner_child_adult.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                gender=gender_array[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
var lastHeightUnit=0
        spinnerHeight.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    if (position==0)
                    {

                        if (lastHeightUnit==0)
                        {

                        }
                        else
                        {

                            edit_text_height.text= Editable.Factory.getInstance().newEditable(((((((edit_text_height.text.toString()).toDouble()*2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))

                        }

                    }
                    else {

                        if (lastHeightUnit==0)
                        {

                            edit_text_height.text= Editable.Factory.getInstance().newEditable(((((((edit_text_height.text.toString().toDouble())/2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }
                        else
                        {

                        }

                    }

                }
                catch(e:Exception)
                {

                }

                    height_uniit=position
                    lastHeightUnit=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
var lastweight_unit=0
        spinner_weight.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    if (position==0)
                    {

                        if (lastweight_unit==0)
                        {

                        }
                        else
                        {

                            edit_text_weight.text= Editable.Factory.getInstance().newEditable(((((((edit_text_weight.text.toString().toDouble())*0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))

                        }

                    }
                    else {

                        if (lastweight_unit==0)
                        {

                            edit_text_weight.text= Editable.Factory.getInstance().newEditable(((((((edit_text_weight.text.toString().toDouble())/0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }
                        else
                        {

                        }

                    }

                }
                catch(e:Exception)
                {

                }

                    weight_uniit=position
                lastweight_unit=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        window.decorView.setOnApplyWindowInsetsListener{view,insets->

        insets
        }

        layout_BMI.setOnClickListener {
            val focucedview=this.currentFocus
            focucedview?.clearFocus()
        }

        button_calculate.setOnClickListener {
        try {
            blackLine.finish=false
            if (height_uniit==0)
            {
                height1=edit_text_height.text.toString().toDouble()
            }
            else
            {
                height1=edit_text_height.text.toString().toDouble()*2.54
            }

            if (weight_uniit==0)
            {
                weight1=edit_text_weight.text.toString().toDouble()
            }
            else
            {
                weight1=edit_text_weight.text.toString().toDouble()*0.453
            }

            BMI_value = weight1 / ((height1 / 100.0).pow(2))

            age=editText_age.text.toString().toDouble()

            var range=0.0
            var BMI_value_relative=0.0
            var k=0

                if (age>20.0) {
                    scale.isVisible=true
                    scale.adult_age=true
                    scale.requestLayout()
                    range=25.0
                    if (BMI_value > 40.0) {
                        BMI_value_relative = range
                    }
                    else if (BMI_value < 15.0) {
                        BMI_value_relative = 0.0
                    }
                    else
                    {
                        BMI_value_relative=BMI_value-15.0
                    }
                    var final_position_of_rect=(BMI_value_relative/range)*(layout_BMI.width.toDouble())

                    var i=0.0
                    val samle_v=final_position_of_rect/400
                    blackLine.isVisible=true
                    if (final_position_of_rect<20.0)
                    {
                        blackLine.finish=true
                        blackLine.BMI_text=(((BMI_value*10).toInt().toDouble())/10.0).toString()
                        blackLine.i=0f
                        blackLine.i_text=0f
                        blackLine.invalidate()

                    }
                    else if (final_position_of_rect>(layout_BMI.width.toDouble()-100.0))
                    {
                        Thread{
                        while(i<=(final_position_of_rect-50.0))
                        {
                           blackLine.i=i.toFloat()
                           runOnUiThread{blackLine.invalidate()}
                            i+=samle_v
                            while (k<1000000)
                            {
                                k++
                            }
                            k=0
                        }
                        blackLine.finish=true
                        blackLine.BMI_text=(((BMI_value*10).toInt().toDouble())/10.0).toString()
                            val Bmi_text_length=(blackLine.BMI_text)!!.length
                            blackLine.i_text=(i-Bmi_text_length*32.0).toFloat()

                            runOnUiThread{blackLine.invalidate()}
                            }.start()

                    }
                    else
                    { Thread{
                        while(i<=final_position_of_rect)
                        {
                            blackLine.i=i.toFloat()
                            runOnUiThread{blackLine.invalidate()}
                            i+=samle_v
                            while (k<1000000)
                            {
                                k++
                            }
                            k=0
                        }
                        blackLine.finish=true
                        blackLine.BMI_text=(((BMI_value*10).toInt().toDouble())/10.0).toString()
                        blackLine.i_text = (i - 60.0).toFloat()
                        runOnUiThread{blackLine.invalidate()}

                    }.start()
                    }
                }
            else
                {
                    var percent5=0.0
                    var percent95=0.0
                if (gender==gender_array[0])
                {

                   percent5=age.pow(2)*0.031-age*0.35+14.8
                    percent95=age.pow(2)*0.057-age*0.579+19.3
                    range = (percent95 + 4.0) - (percent5 - 4.0)
                    if (BMI_value > (percent95 + 4.0)) {
                        BMI_value_relative = range
                    }
                    else if (BMI_value < (percent5 - 4.0)) {
                        BMI_value_relative = 0.0
                    }
                    else
                    {
                        BMI_value_relative=BMI_value-(percent5 - 4.0)
                    }
                    scale.isVisible=true
                    scale.adult_age=false
                    scale.requestLayout()
                    var final_position_of_rect=(BMI_value_relative/range)*(layout_BMI.width.toDouble())
                    var i=0.0
                    val samle_v=final_position_of_rect/400
                    blackLine.isVisible=true
                    if (final_position_of_rect<20.0)
                    {
                        blackLine.finish=true
                        blackLine.BMI_text=(((BMI_value*10).toInt().toDouble())/10.0).toString()
                        blackLine.i_text=0f
                        blackLine.i=0f
                        blackLine.invalidate()
                    }
                    else if (final_position_of_rect>layout_BMI.width.toDouble()-20.0)
                    {
                        Thread {
                            while (i <= (final_position_of_rect - 20.0)) {
                                blackLine.i = i.toFloat()
                                runOnUiThread{blackLine.invalidate()}
                                i += samle_v
                                while (k<1000000)
                                {
                                    k++
                                }
                                k=0
                            }
                            blackLine.finish = true
                            blackLine.BMI_text =
                                (((BMI_value * 10).toInt().toDouble()) / 10.0).toString()
                            val Bmi_text_length=(blackLine.BMI_text)!!.length
                            blackLine.i_text=(i-Bmi_text_length*32.0).toFloat()
                            runOnUiThread{blackLine.invalidate()
                                }
                        }.start()

                    }
                    else {
                        Thread {
                        while (i <= final_position_of_rect) {
                            blackLine.i = i.toFloat()
                            runOnUiThread { blackLine.invalidate() }
                            i += samle_v
                            while (k < 1000000) {
                                k++
                            }
                            k = 0
                        }
                        blackLine.finish = true
                        blackLine.BMI_text =
                            (((BMI_value * 10).toInt().toDouble()) / 10.0).toString()

                            blackLine.i_text=(i-60.0).toFloat()
                        runOnUiThread { blackLine.invalidate() }

                    }.start()
                    }
                }
                    else
                {
                    percent5=-age.pow(3)*0.0009+age.pow(2)*0.046-age*0.4+14.2
                    percent95=-age.pow(3)*0.00195+age.pow(2)*0.1-age*0.49+19.6
                    range = (percent95 + 4.0) - (percent5 - 4.0)
                    if (BMI_value > (percent95 + 4.0)) {
                        BMI_value_relative = range
                    }
                    else if (BMI_value < (percent5 - 4.0)) {
                        BMI_value_relative = 0.0
                    }
                    else
                    {
                        BMI_value_relative=BMI_value-(percent5 - 4.0)
                    }
                    scale.isVisible=true
                    scale.adult_age=false
                    scale.requestLayout()
                    var final_position_of_rect=(BMI_value_relative/range)*(layout_BMI.width.toDouble())
                    var i=0.0
                    val samle_v=final_position_of_rect/400
                    blackLine.isVisible=true
                    if (final_position_of_rect<20.0)
                    {
                        blackLine.finish=true
                        blackLine.BMI_text=(((BMI_value*10).toInt().toDouble())/10.0).toString()
                        blackLine.i_text=0f
                        blackLine.i=0f
                        blackLine.invalidate()
                    }
                    else if (final_position_of_rect>layout_BMI.width.toDouble()-20.0)
                    {

                        Thread {while(i<=(final_position_of_rect-20.0))
                        {
                            blackLine.i=i.toFloat()
                            runOnUiThread{blackLine.invalidate()}
                            i+=samle_v
                            while (k<1000000)
                            {
                                k++
                            }
                            k=0
                        }
                        blackLine.finish=true
                        blackLine.BMI_text=(((BMI_value*10).toInt().toDouble())/10.0).toString()
                            val Bmi_text_length=(blackLine.BMI_text)!!.length
                            blackLine.i_text=(i-Bmi_text_length*32.0).toFloat()

                        runOnUiThread{blackLine.invalidate()}
                            }.start()

                    }
                    else
                    {Thread {
                        while(i<=final_position_of_rect) {

                                blackLine.i = i.toFloat()
                                runOnUiThread { blackLine.invalidate() }
                                i += samle_v
                                while (k < 1000000) {
                                    k++
                                }
                                k = 0
                            }
                            blackLine.finish = true
                            blackLine.BMI_text =
                                (((BMI_value * 10).toInt().toDouble()) / 10.0).toString()
                        blackLine.i_text=(i-60.0).toFloat()
                            runOnUiThread { blackLine.invalidate() }

                        }.start()
                    }
                }

                }

        }
        catch (e:Exception)
        {
            val text = "Put all information correctly"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this, text, duration)
            toast.show()
        }
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {

        val msg=Message.obtain(handler1,1,1,1)
        handler1.sendMessage(msg)
        super.onConfigurationChanged(newConfig)
    }
}