package elo.viceroy.bodyfat1

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.*
import android.text.Editable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.*

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class BurnCalorieActivity: AppCompatActivity() {
    var weighttUnitArray=arrayOf<String>("kg","lbs")
    var genderArray=arrayOf<String>("Male","Female")
    var speedUnits=arrayOf("km/h","min/100m","min/km")
    var speedUnit=0
    lateinit var handler1:Handler
    var speed_to_calculate=-1.0
    var listofelemrntspopup= mutableListOf<elements>()
    var listofkindofactivities= mutableListOf("Household duties","Cardio","Team sports","Work","Fitness")
    var listofspecificactivities= mutableListOf<Array<String>>(
        arrayOf("Dish washing","Mow lawn", "Tidying", "Cooking", "Ironing", "Babysitting","Gardening","Raking Leaves","Wood Chopping","Wall Painting","Furniture Assembling","Car Washing","Snow Removal"),
        arrayOf("Jogging","Running/Walking","Cycling", "Mountain Biking", "Cross-county cycling","BMX",
        "Swimming Breaststroke ","Swimming Freestyle","Swimming Butterfly","Swimming Backstroke ","Speed Skating/Skating","Cross-country Skiing","Canoeing","Rowing",
        "Ballroom Dancing","Street Dance","Luge","Skiing Average Peace","Skiing Fast Peace",
        "Ski Jumping","Snowboard Average Peace","Snowboard Fast Peace"),
        arrayOf("Football", "American Football", "Rugby", "Handball","Volleyball", "Beach Volleyball", "Basketball","Ice Hockey", "Baseball" ),
        arrayOf("Bartender","Office Work", "Electrician", "Assembler Sitting Work","Assembler Standing Work","Pilot", "Car Mechanic", "Lorry Driver","Elderly Caregiver","Builder" ),
        arrayOf("Pilates","Joga", "Tabata Training","Weightlifting","Gym Workout- Exercise Machines", "Stretching", "Trampoline jumping", "Skipping-rope Average Peace","Skipping-rope Fast Peace","Calisthenics","Sports Gymnastics", "Spinning" ),


    )
    /* all coefs 100min 100kg*/
    /* running coef for 10 km/h, cycling for 20km/h, Swimming breaststroke 2,7 km/h (2:10/100m) , swimming freestyle 3,2km/h (1:50 min/100m),
    swimming Butterfly 3 km/h , swimmming Back stroke 3km/h (2:00 min/100m), skating 20 km/h , cross country skiing 12 km/h*, canoeing 4,5 km/h,rowing 4,5 km/h*/
    var listofspecificactivitiesmenCoef= mutableListOf<Array<Double>>(
        arrayOf(0.038,0.07,0.06,0.025,0.03,0.038,0.07,0.066,0.09,0.055,0.075,0.07,0.09),
        arrayOf(0.1,0.175,0.133,0.141,0.15,0.14,0.21,0.21,0.21,0.21,0.13,0.125,0.05,0.05,0.09,0.1,0.1,0.09,0.12,0.1,0.091,0.123),
    arrayOf(0.15,0.165,0.165,0.2,0.065,0.133,0.133,0.133,0.11),
    arrayOf(0.03,0.02,0.04,0.025,0.04,0.02,0.05,0.02,0.038,0.06),
    arrayOf(0.05,0.0416,0.16,0.09,0.06,0.0416,0.07,0.12,0.15,0.1,0.1,0.13)

        )

    var gender:String=genderArray[0]
    var kind_of_activity=0
    var specific_activity=0
    var caloriesBurned=-1.0
    var weight=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.burn_calorie_activity)
        val spinnerGender = findViewById<Spinner>(R.id.gender)
        spinnerGender.isVisible=false
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        val animation= AnimationUtils.loadAnimation(this,R.anim.from_small_to_big)
        val editTextWeight = findViewById<EditText>(R.id.weight_to_put)
        val spinnerWeightUnit = findViewById<Spinner>(R.id.weightUnits1)
        val spinerKindOfActivity = findViewById<Spinner>(R.id.kind_of_activities)
        val spinerSpecificActivity = findViewById<Spinner>(R.id.specific_activity)
        val buttonCalcualte=findViewById<Button>(R.id.Calculate)
        val textViewCaloriesBurned=findViewById<TextView>(R.id.BurnedCalories)
        val edittextDuration=findViewById<EditText>(R.id.duration)
        val editTextspeed=findViewById<EditText>(R.id.speed)
        val edit_text_min=findViewById<EditText>(R.id.speed_min_100m_min)
        val spinner_speed_unit=findViewById<Spinner>(R.id.speed_unit)
        val textviewtp=findViewById<TextView>(R.id.tekstview123)
        var layout_Burn=findViewById<ConstraintLayout>(R.id.BurnCalorieLayout)
        var popupView=this.layoutInflater.inflate(R.layout.new_for_popup,null)
        val imagepaper=popupView.findViewById<ImageView>(R.id.imagepapaer)
        val recyclerscrollview=popupView.findViewById<HorizontalScrollView>(R.id.horizonscroll)
        val recyclerpopup=popupView.findViewById<RecyclerView>(R.id.recyclerpopup)


        var textforinstr="This calculator estimate the amount\nof burned calories during various activities.\n" +
                "To loose weight, it's necessary\nto keep negative calorie balance.\n"+
                "To loose 1 kg of fat you need burn about 7000 kcal.\n"+
                "The most accurate way to estimate amount of calories\nyou need eat everyday to loose specific weight\n"+
                "is to compute the basic metabolism\n(use Calorie need calculator from this app)\n"+
                "and add to this everyday amount of burned calories\n(computed by this calculator).\n"+
                "Quantity of negative calorie balance will define the pace\nof weight loss."
        listofelemrntspopup.add(elements("Calorie burn", null))
        listofelemrntspopup.add(elements(textforinstr, null))


        recyclerpopup.layoutManager= LinearLayoutManager(this)
        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this)
        imagepaper.scaleType=ImageView.ScaleType.FIT_XY
        val btmp= BitmapFactory.decodeResource(resources,R.drawable.popup1)

        /*val textup=findViewById<TextView>(R.id.tekstview1234)*/
        Thread {
            val intpstrm = resources.openRawResource(R.raw.popupphoto)
            val bytearray = intpstrm.readBytes()
            intpstrm.close()
            var intarrray =
                bytearray.decodeToString().split(";").toTypedArray().map { it.toInt() }.toIntArray()
            var btmp1= Bitmap.createBitmap(intarrray,500,500,btmp.config)
            var matrix= Matrix().also{ block->block.postRotate(90f)}
            btmp1= Bitmap.createBitmap(btmp1,0,0,btmp1.width,btmp1.height,matrix,true)

            runOnUiThread{
                imagepaper.setImageBitmap(btmp1)
            }
        }.start()
        val popupbackground=popupView.findViewById<ConstraintLayout>(R.id.popupbackground1)
        val popuupWindow=PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val imageQuestion=findViewById<ImageView>(R.id.questionBurned)
        imageQuestion.setOnClickListener {
            requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LOCKED
            popuupWindow.isOutsideTouchable = true
            popuupWindow.isFocusable = true
            /*popuupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))*/
            popuupWindow.showAsDropDown(layout_Burn)
        }
        popupbackground.setOnClickListener {
            if (popuupWindow.isShowing)
            {
                popuupWindow.dismiss()
                requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }

        }

        handler1=object :Handler(Looper.getMainLooper())
        {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what==1)
                {
                    /*textviewtp.text=resources.configuration.orientation.toString()*/
                    if (resources.configuration.orientation==android.content.res.Configuration.ORIENTATION_LANDSCAPE)
                    {
                        imagepaper.layoutParams.width=
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,650f,resources.displayMetrics).toInt()
                        imagepaper.layoutParams.height=ConstraintLayout.LayoutParams.MATCH_PARENT
                        imagepaper.invalidate()
                        recyclerscrollview.layoutParams.width=
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,500f,resources.displayMetrics).toInt()
                        recyclerscrollview.layoutParams.height=
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,260f,resources.displayMetrics).toInt()
                        recyclerpopup.layoutManager=LinearLayoutManager(this@BurnCalorieActivity)
                        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this@BurnCalorieActivity)
                        popupbackground.forceLayout()
                    }
                    else

                    {
                        imagepaper.layoutParams.width=ConstraintLayout.LayoutParams.MATCH_PARENT
                        imagepaper.layoutParams.height=
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,500f,resources.displayMetrics).toInt()
                        imagepaper.invalidate()
                        recyclerscrollview.layoutParams.width=
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,300f,resources.displayMetrics).toInt()
                        recyclerscrollview.layoutParams.height=
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,360f,resources.displayMetrics).toInt()
                        recyclerpopup.layoutManager=LinearLayoutManager(this@BurnCalorieActivity)
                        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this@BurnCalorieActivity)
                        popupbackground.forceLayout()

                    }
                }

            }
        }



        val drawable_sp_icons = ContextCompat.getDrawable(this,R.drawable.sports_icon)?.apply {
            setBounds(0, 0, 300, 300)
        }
        textviewtp.setCompoundDrawables(null, drawable_sp_icons,null,null)
        edit_text_min.isVisible=false
        editTextspeed.isVisible=false
        spinner_speed_unit.isVisible=false
        val adapter_speed_unit = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return speedUnits.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=speedUnits[position]+ "  "
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
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=speedUnits[position]
                return view
            }
        }


        val adapter_weight_unit = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return weighttUnitArray.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
               val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=weighttUnitArray[position]
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
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=weighttUnitArray[position]
                return view
            }
        }

        val adapter_gender = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return genderArray.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=genderArray[position]
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
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=genderArray[position]
                return view
            }
        }

        val adapter_kind_of_activity = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return listofkindofactivities.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)

                textView.setTypeface(null, Typeface.BOLD_ITALIC)
                textView.text=listofkindofactivities[position]

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
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=listofkindofactivities[position]
                return view
            }
        }

        var adapter_specific_activity = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return listofspecificactivities[kind_of_activity].size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.setTypeface(null, Typeface.BOLD_ITALIC)
                textView.text=listofspecificactivities[kind_of_activity][position]
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
                val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=listofspecificactivities[kind_of_activity][position]
                return view
            }
        }

        spinnerGender.adapter=adapter_gender
        spinerKindOfActivity.adapter=adapter_kind_of_activity
        spinerSpecificActivity.adapter=adapter_specific_activity
        spinnerWeightUnit.adapter=adapter_weight_unit
        spinner_speed_unit.adapter=adapter_speed_unit

        spinnerGender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                gender=genderArray[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        var lastWeightUnit=0
        var weightUnit:String=weighttUnitArray[0]
        spinnerWeightUnit.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    val actualWeight=editTextWeight.text.toString().toDouble()
                    if (position==0)
                    {
                        if (lastWeightUnit==0)
                        {
                        }
                        else
                        {
                            editTextWeight.text = Editable.Factory.getInstance().newEditable(((((((actualWeight*0.453)*10.0).toInt()).toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                    else if(position==1)
                    {
                        if (lastWeightUnit==1)
                        {
                        }
                        else
                        {
                            editTextWeight.text = Editable.Factory.getInstance().newEditable(((((((actualWeight/0.453)*10.0).toInt()).toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                }
                catch (e:Exception)
                {

                }

                    weightUnit=weighttUnitArray[position]
                    lastWeightUnit=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinerKindOfActivity.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                kind_of_activity=position
                val adapter_specific_activity = object : ArrayAdapter<Any>(
                    this@BurnCalorieActivity,
                    R.layout.vievfordropdownlist
                )
                {
                    override fun getCount(): Int {
                        return listofspecificactivities[kind_of_activity].size
                    }

                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                        val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                        textView.setTypeface(null, Typeface.BOLD_ITALIC)
                        textView.text=listofspecificactivities[kind_of_activity][position]
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
                        val view=this@BurnCalorieActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                        val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                        textView.text=listofspecificactivities[kind_of_activity][position]
                        return view
                    }

                }
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed(Runnable {spinerSpecificActivity.adapter=adapter_specific_activity
                }, 50)



            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
         spinerSpecificActivity.onItemSelectedListener=object :
              AdapterView.OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>,
                  view: View, position: Int, id: Long
              ) {
                  specific_activity=position
                  if (kind_of_activity==1 && (position==1||position==11||position==12||position==13||position==2||position==10||position==6||position==7||position==8||position==9))
                  {
                      editTextspeed.isVisible=true
                      spinner_speed_unit.isVisible=true
                      editTextspeed.isVisible=true
                      if (speedUnit==0) {
                          editTextspeed.hint = "Speed [km/h]"
                      }
                      else
                      {
                          editTextspeed.hint = "s"
                      }

                  }

                  else
                  {
                      editTextspeed.isVisible=false
                      spinner_speed_unit.isVisible=false
                      edit_text_min.isVisible=false

                  }
              }
              override fun onNothingSelected(parent: AdapterView<*>) {

              }
          }

          var last_speed_unit=0
          spinner_speed_unit.onItemSelectedListener=object :
              AdapterView.OnItemSelectedListener {
              override fun onItemSelected(
                  parent: AdapterView<*>,
                  view: View, position: Int, id: Long
              ) {
                  speedUnit=position
                  var speed_km_h:Double?=null
                  var speed_m:Double?=null
                  var speed_s:Double?=null
                  if (last_speed_unit==0)
                  {
                      try {
                          speed_km_h = editTextspeed.text.toString().toDouble()
                      }
                      catch (e:Exception)
                      {}

                  }
                  else if (last_speed_unit==1)
                  {try {
                      speed_m= edit_text_min.text.toString().toDouble()
                      speed_s=editTextspeed.text.toString().toDouble()
                  }
                  catch (e:Exception)
                  {}

                  }
                  else if (last_speed_unit==2)
                  {
                      try {
                      speed_m= edit_text_min.text.toString().toDouble()
                      speed_s=editTextspeed.text.toString().toDouble()
                  }
                  catch (e:Exception)
                  {}

                  }

                  if (position==0)
                  {
                      if (last_speed_unit==0)
                      {

                      }
                      else if (last_speed_unit==1)
                      {
                          if (speed_m!=null && speed_s!=null) {
                              edit_text_min.isVisible = false
                              editTextspeed.hint="Speed [km/h]"
                              editTextspeed.text = Editable.Factory.getInstance().newEditable(
                                  ((((60.0/(10*(speed_m+speed_s/60.0)))*10.0).toInt().toDouble()/10.0)).toString()
                              )
                          }
                          else
                          {
                              editTextspeed.hint="Speed [km/h]"

                              edit_text_min.text=Editable.Factory.getInstance().newEditable("")
                              editTextspeed.requestFocus()
                              edit_text_min.hint=""
                          }
                      }
                      else if (last_speed_unit==2)
                      {
                          if (speed_m!=null && speed_s!=null) {
                              edit_text_min.isVisible = false
                              editTextspeed.hint="Speed [km/h]"
                              editTextspeed.text = Editable.Factory.getInstance().newEditable(
                                  ((((60.0/(speed_m+speed_s/60.0))*10.0).toInt().toDouble()/10.0)).toString()
                              )
                          }
                          else
                          {
                              editTextspeed.hint="Speed [km/h]"
                              editTextspeed.requestFocus()
                              edit_text_min.text=Editable.Factory.getInstance().newEditable("")
                              edit_text_min.hint=""
                          }
                      }
                  }
                  else if (position==1)
                  {
                      if (last_speed_unit==0)
                      {
                          if (speed_km_h!=null) {
                              edit_text_min.isVisible = true
                              edit_text_min.hint="min"
                              editTextspeed.hint="sec"
                              edit_text_min.text = Editable.Factory.getInstance()
                                  .newEditable(((60.0/(10.0*speed_km_h)).toInt()).toString())
                              editTextspeed.text = Editable.Factory.getInstance()
                                  .newEditable(((((60.0/(10.0*speed_km_h))-(60.0/(10.0*speed_km_h)).toInt().toDouble())*60.0).roundToInt()).toString())
                          }
                          else
                          {
                              edit_text_min.isVisible = true
                              edit_text_min.hint="min"
                              editTextspeed.hint="sec"
                          }


                      }
                      else if (last_speed_unit==1)
                      {

                      }
                      else if (last_speed_unit==2)
                      {
                          if (speed_m!=null && speed_s!=null) {
                              edit_text_min.text = Editable.Factory.getInstance()
                                  .newEditable(((((speed_m*60.0+speed_s)/10.0))/60.0).toInt().toString())
                              editTextspeed.text = Editable.Factory.getInstance()
                                  .newEditable(((((((speed_m*60.0+speed_s)/10.0))/60.0)-(((((speed_m*60.0+speed_s)/10.0))/60.0).toInt()).toDouble())*60.0).roundToInt().toString())
                          }
                          else
                          {
                              edit_text_min.hint="min"
                              editTextspeed.hint="sec"
                          }
                      }


                  }
                  else if (position==2)
                  {
                      if (last_speed_unit==0)
                      {
                          if (speed_km_h!=null) {
                              edit_text_min.isVisible = true
                              edit_text_min.hint="min"
                              editTextspeed.hint="sec"
                              edit_text_min.text = Editable.Factory.getInstance()
                                  .newEditable((60.0/(speed_km_h)).toInt().toString())
                              editTextspeed.text = Editable.Factory.getInstance()
                                  .newEditable((((60.0/(speed_km_h))-(60.0/(speed_km_h)).toInt().toDouble())*60.0).roundToInt().toString())
                          }
                          else
                          {
                              edit_text_min.isVisible = true
                              edit_text_min.hint="min"
                              editTextspeed.hint="sec"
                          }
                      }
                      else if (last_speed_unit==1)
                      {
                          if (speed_m!=null && speed_s!=null) {
                              edit_text_min.text = Editable.Factory.getInstance()
                                  .newEditable(((((speed_m*60.0+speed_s)*10.0))/60.0).toInt().toString())
                              editTextspeed.text = Editable.Factory.getInstance()
                                  .newEditable(((((((speed_m*60.0+speed_s)*10.0))/60.0)-((((speed_m*60.0+speed_s)*10.0))/60.0).toInt().toDouble())*60.0).roundToInt().toString())
                          }
                          else
                          {
                              edit_text_min.hint="min"
                              editTextspeed.hint="sec"
                          }

                      }
                      else if (last_speed_unit==2)
                      {

                      }


                  }
                  last_speed_unit=position

              }
              override fun onNothingSelected(parent: AdapterView<*>) {

              }
          }

        layout_Burn.setOnClickListener {
            val focucedview=this.currentFocus
            focucedview?.clearFocus()
        }

          buttonCalcualte.setOnClickListener {
              try {
                  if (weightUnit==weighttUnitArray[0])
                  {
                      weight=editTextWeight.text.toString()
                          .toDouble()
                  }
                  else
                  {
                      weight=editTextWeight.text.toString()
                          .toDouble()*0.453
                  }
              if (kind_of_activity == 1 && (specific_activity == 1 || specific_activity == 11 || specific_activity == 12 || specific_activity == 13 || specific_activity == 2 || specific_activity == 10 || specific_activity == 6 || specific_activity == 7 || specific_activity == 8 || specific_activity == 9)) {


                      if (last_speed_unit == 0) {
                          speed_to_calculate = (editTextspeed.text.toString().toDouble())*1000.0/3600.0
                      } else if (last_speed_unit == 1) {
                          speed_to_calculate =( 3600.0 / ((edit_text_min.text.toString()
                              .toDouble() * 60.0 + editTextspeed.text.toString().toDouble()) * 10))*1000.0/3600.0
                      } else if (last_speed_unit == 2) {
                          speed_to_calculate =( 3600.0 / (edit_text_min.text.toString()
                              .toDouble() * 60.0 + editTextspeed.text.toString().toDouble()))*1000.0/3600.0
                      }

                        var Co:Double /*współczynnik oporu powietrza/wody (wielkość bezwymiarowa)*/
                        var ro:Double /*gęstość powietrz/wody (generalnie czynnika w którym się poruszamy) [kg/m^3]*/
                  var Af:Double /*powierzchnia czołowa wystawiona na opór czynnika [m^2]*/
                  var k_dv_dtk:Double /*szybkość wzrostu prędkości podczs wykonywania ruchu (np. czsu nacisku na pedał roweru) */
                  var v0:Double /*prędkośc początkowa wykonywania ruchu przyspieszającego [m/s]- liniowa*/
                  var vk:Double /*prędkośc końcowa po zakończeniu ruchu [m/s]-liniowa*/
                  var tk:Double /*czas trwania ruchu [s]*/
                  var Ct:Double /*współczynnik tarcia (tocznego liniowego[dynamiczny])*/

                  var m:Double /*masa ciałą poruszającego się (człowiek + sprzęt) [kg]*/
                  var g=9.8 /*przyspieszenie ziemskie*/
                  var t_activ=edittextDuration.text.toString().toDouble()
                  var v0_2:Double /*prędkośc początkowa wykonywania ruchu przyspieszającego [m/s]- w drugiej osi- pionowa bąż pozioma*/
                  var vk_2:Double /*prędkośc końcowa po zakończeniu ruchu [m/s]-w drugiej osi- pionowa bąż pozioma*/

                      if (speed_to_calculate >= 0) {

                          if (specific_activity == 1) {
                              Co=0.6
                              ro=1.2
                              Af=0.6
                              tk=0.25
                              Ct=1.0
                              m=weight
                              vk=speed_to_calculate*1.035
                              v0=speed_to_calculate*0.965
                              var cadence=180.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077*(1-exp(-(speed_to_calculate*3.6)/3))

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk

                             /* caloriesBurned =
                                3.3*  (( (0.5*Co*ro*Af*(0.25*k_dv_dtk.pow(3)*tk.pow(4)+(3.0/2.0)*k_dv_dtk*v0.pow(2)*
                                          tk.pow(2)+k_dv_dtk.pow(2)*v0*tk.pow(3)+v0.pow(3)*tk)+(0.5*m*vk.pow(2)-0.5*m*v0.pow(2))+
                                          (m*g*h_m_w)) *
                                          (t_activ*cadence))/4184.0)*/

                                  caloriesBurned =
                                      3.3*(1-exp(-(speed_to_calculate*3.6)/3)) * (0.5 * Co * ro * Af * speed_to_calculate.pow(3) * t_activ * 60 + ((0.5 * m * vk.pow(
                                          2
                                      ) - 0.5 * m * v0.pow(2)) + m * g * h_m_w) * (t_activ * cadence)) / 4184



                          }
                          if (specific_activity == 2) {
                              Co=0.3
                              ro=1.2
                              Af=0.35
                              Ct=0.04
                              m=weight

                              caloriesBurned =4*(0.5*Co*ro*Af*speed_to_calculate.pow(2)+Ct*m*g)*speed_to_calculate*(t_activ/60.0)*0.8598

                          }
                          if (specific_activity == 6) {
                              Co=0.6
                              ro=1000.0
                              Af=0.58
                              tk=3.0
                              Ct=0.005
                              m=weight
                              vk=speed_to_calculate*1.02
                              v0=speed_to_calculate*0.98
                              var cadence=18.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk

                              caloriesBurned =
                                  5.7* (((-atan(speed_to_calculate*3.6-3.0)+3.1415/2.0)/3.1415)+1)* ((0.5*Co*ro*Af*speed_to_calculate.pow(3)*t_activ*60 + (Ct*m*g*h_m_w)*(t_activ*cadence) )/4184) +50

                          }
                          if (specific_activity == 7) {
                              Co=0.6
                              ro=1000.0
                              Af=0.55
                              tk=3.0
                              Ct=0.005
                              m=weight
                              vk=speed_to_calculate*1.02
                              v0=speed_to_calculate*0.98
                              var cadence=18.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk

                              caloriesBurned =
                                  5.5* (((-atan(speed_to_calculate*3.6-3.0)+3.1415/2.0)/3.1415)+1)* ((0.5*Co*ro*Af*speed_to_calculate.pow(3)*t_activ*60 + (Ct*m*g*h_m_w)*(t_activ*cadence) )/4184) +50
                          }
                          if (specific_activity == 8) {
                              Co=0.6
                              ro=1000.0
                              Af=0.6
                              tk=3.0
                              Ct=0.005
                              m=weight
                              vk=speed_to_calculate*1.02
                              v0=speed_to_calculate*0.98
                              var cadence=18.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk

                              caloriesBurned =
                                  5.5* (((-atan(speed_to_calculate*3.6-3.0)+3.1415/2.0)/3.1415)+1)* ((0.5*Co*ro*Af*speed_to_calculate.pow(3)*t_activ*60 + (Ct*m*g*h_m_w)*(t_activ*cadence) )/4184) +50


                          }
                          if (specific_activity == 9) {
                              Co=0.6
                              ro=1000.0
                              Af=0.55
                              tk=3.0
                              Ct=0.005
                              m=weight
                              vk=speed_to_calculate*1.02
                              v0=speed_to_calculate*0.98
                              var cadence=18.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk

                              caloriesBurned =
                                  5.8* (((-atan(speed_to_calculate*3.6-3.0)+3.1415/2.0)/3.1415)+1)* ((0.5*Co*ro*Af*speed_to_calculate.pow(3)*t_activ*60 + (Ct*m*g*h_m_w)*(t_activ*cadence) )/4184)+50
                          }
                          if (specific_activity == 10) {
                              Co=0.3
                              ro=1.2
                              Af=0.6
                              Ct=0.03
                              tk=1.0
                              m=weight
                              vk=speed_to_calculate*0.5
                              v0=0.0
                              var cadence=25.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              caloriesBurned =
                                  3.0 * (((0.5*Co*ro*Af*speed_to_calculate.pow(3)+Ct*m*g*speed_to_calculate)*t_activ*60+
                                          (((Ct*m*g*(0.5*tk.pow(2))+0.5*m*vk.pow(2)-0.5*m*v0.pow(2)+0.5*Co*ro*Af*(0.25*k_dv_dtk.pow(3)*tk.pow(4)+(3.0/2.0)*k_dv_dtk*v0.pow(2)*
                                                  tk.pow(2)+k_dv_dtk.pow(2)*v0*tk.pow(3)))*t_activ*cadence)))/4184)
                          }
                          if (specific_activity == 11) {
                              Co=0.6
                              ro=1.2
                              Af=0.85
                              Ct=0.05
                              m=weight

                              caloriesBurned =
                                  4.65 * ((0.5*Co*ro*Af*speed_to_calculate.pow(3)+Ct*m*g*speed_to_calculate)*t_activ*60 /4184)
                          }
                          if (specific_activity == 12) {
                              Co=0.1
                              ro=1000.0
                              Af=0.1
                              var Co_1=0.6
                              var ro_1=1.2
                              var Af_1=0.8
                              tk=2.8
                              Ct=0.005
                              m=weight
                              vk=speed_to_calculate*1.02
                              v0=speed_to_calculate*0.98
                              var cadence=20.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk
                              caloriesBurned =
                                  5.7* (((-atan(speed_to_calculate*3.6-9.0)+3.1415/2.0)/3.1415)+1)* ((0.5*Co*ro*Af*speed_to_calculate.pow(3)+0.5*Co_1*ro_1*Af_1*speed_to_calculate.pow(3))*t_activ*60 /4184) +80 }
                          if (specific_activity == 13) {
                              Co=0.1
                              ro=1000.0
                              Af=0.1
                              var Co_1=0.6
                              var ro_1=1.2
                              var Af_1=0.8
                              tk=2.8
                              Ct=0.005
                              m=weight
                              vk=speed_to_calculate*1.02
                              v0=speed_to_calculate*0.98
                              var cadence=20.0
                              vk_2=1.6
                              v0_2=0.0
                              var h_m_w=0.077

                              k_dv_dtk=(vk-v0)/tk
                              var k2_dv_dtk=(vk_2-v0_2)/tk
                              caloriesBurned =
                                  5.3* (((-atan(speed_to_calculate*3.6-9.0)+3.1415/2.0)/3.1415)+1) * ((0.5*Co*ro*Af*speed_to_calculate.pow(3)+0.5*Co_1*ro_1*Af_1*speed_to_calculate.pow(3))*t_activ*60 /4184)+80
                          }

                      }
                  }else {
                          caloriesBurned =
                              listofspecificactivitiesmenCoef[kind_of_activity][specific_activity] *weight * edittextDuration.text.toString().toDouble()


                      }
                  }
              catch (e:Exception)
              {
                  val text = "Put all information correctly"
                  val duration = Toast.LENGTH_SHORT
                  val toast = Toast.makeText(this, text, duration)
                  toast.show()
              }



              if (caloriesBurned>=-0.1) {
                  textViewCaloriesBurned.text = (caloriesBurned.toInt().toString() +" "+ "kcal").format(
                      Locale.US, "%.2f"
                  )
                  textViewCaloriesBurned.startAnimation(animation)
              }


          }

    }
    override fun onConfigurationChanged(newConfig: Configuration) {

        val msg= Message.obtain(handler1,1,1,1)
        handler1.sendMessage(msg)
        super.onConfigurationChanged(newConfig)
    }
}