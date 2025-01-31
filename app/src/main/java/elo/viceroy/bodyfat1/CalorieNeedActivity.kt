package elo.viceroy.bodyfat1

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.os.*
import android.text.Editable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.roundToInt

class CalorieNeedActivity:AppCompatActivity() {
    val sorsman_normal_array=arrayOf("Sportsman","Normal")
    val gender_array=arrayOf("Male","Female")
    val heightUnit_array=arrayOf("cm","inch")
    val weightUnit_array=arrayOf("kg","lbs")
    var listofelemrntspopup= mutableListOf<elements>()
    var listofelemrntspopupBFtip= mutableListOf<elements>()
    val activity_amount_array=arrayOf("Sitting Lifestyle", "Light physical work, no exercising", "Hard physical work, no exercises",
        "Light physical work,light exercising (2-3 times a week)", "Light physical work,hard exercising (4-7 times a week)",
        "Hard physical work, light exercising (2-3 times a week)","Hard physical work,hard exercising (4-7 times a week)",
        )
    val activity_amount_coefs=arrayOf(1.2,1.4,1.6,1.55,1.8,2.0,2.2)
    var chosen_activity_amount=0
    var gender_value=gender_array[0]
    var height_uniit=0
    var weight_uniit=0
    var calculationOptionChosen=0
    var weight1=0.0
    var height1=0.0
    var age1=0.0
    var sportsman_normal=sorsman_normal_array[0]
    var calorieRequirementsValueforText=0.0
    lateinit var handler1:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calorie_need_activity)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    val spinner_gender=findViewById<Spinner>(R.id.gender123)
        val animation= AnimationUtils.loadAnimation(this,R.anim.from_small_to_big)
        val check_box_base=findViewById<CheckBox>(R.id.base_calorie_need)
        check_box_base.isChecked=true
        /*check_box_base.text="Basic Metabolism"*/
        val check_box_activity=findViewById<CheckBox>(R.id.calorie_need_with_activity)
        check_box_activity.isChecked=false
       /*check_box_activity.text="Calorie Requirement"*/
        val edit_text_age=findViewById<EditText>(R.id.Age_calorie_need)
        edit_text_age.isVisible=false
        val edit_text_weight=findViewById<EditText>(R.id.weight_calorie_need)
        val edit_text_height=findViewById<EditText>(R.id.height_calorie_need)
        edit_text_height.isVisible=false
        val spinner_weight_units=findViewById<Spinner>(R.id.weightUnits12)
        val spinner_height_units=findViewById<Spinner>(R.id.heightunits12)
        spinner_height_units.isVisible=false
        val spinner_sprtsman_normal=findViewById<Spinner>(R.id.sprtsman_normal)
        val edit_tex_BF=findViewById<EditText>(R.id.body_fat_percentage)
        edit_tex_BF.isVisible=false
        val activity_amount=findViewById<Spinner>(R.id.activity_amount)
        activity_amount.isVisible=false
        val buttton_Calc=findViewById<Button>(R.id.Calculate123)
        val text_calorie_requirements=findViewById<TextView>(R.id.Calorie_Requiremnts)
        val textviewtp=findViewById<TextView>(R.id.tekstview12345)
        var layout_Need=findViewById<ConstraintLayout>(R.id.CalorieNeedLayout)
        var popupView=this.layoutInflater.inflate(R.layout.new_for_popup,null)
        val imagepaper=popupView.findViewById<ImageView>(R.id.imagepapaer)
        val recyclerscrollview=popupView.findViewById<HorizontalScrollView>(R.id.horizonscroll)
        val recyclerpopup=popupView.findViewById<RecyclerView>(R.id.recyclerpopup)
        val image_BF_tip=findViewById<ImageView>(R.id.BF_tip)

            val text_for_intruction="This calculator compute the basic  personal daily calories requirement\nand total daily requirement in relation to\nsport activity and kind of profession." +
                    "User can select two ways for computing basic calories requirement.\nFirst is Normal, the Harris-Benedick equation is used,\nthe second is Sportsmen, the Cunningham equation is used.\n\n" +
                    "Harrison_Benedict equation:\n"+
                "For men: 66,5 + (13,75 x body weight [kg]) + (5,003 x height [cm]) – (6,775 x [age])\n"+
                "For women: 655,1 + (9,563 x body weight [kg]) + (1,85 x height [cm]) – (4,676 x [age])\n\n"+
                        "Cunningham equation:\n"+
                        "For both genders: 500+ (lean body mass [kg]* 22)\n\n" +
                "Calculation of total daily requirement is done by multiplying the computed basic value by activity coefficient."
            val text_for_BF="Tip:\nYou can use\nthe result\nfrom Body Fat\ncalculator"

        listofelemrntspopup.add(elements("Calorie requirement", null))
        listofelemrntspopup.add(elements(text_for_intruction, null))
        listofelemrntspopupBFtip.add(elements(text_for_BF,null))


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
        val imageQuestion=findViewById<ImageView>(R.id.questionNeed)
        imageQuestion.setOnClickListener {
            requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LOCKED
            recyclerpopup.layoutManager= LinearLayoutManager(this)
            recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this)
            popuupWindow.isOutsideTouchable = true
            popuupWindow.isFocusable = true
           /* popuupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))*/
            popuupWindow.showAsDropDown(imageQuestion)
        }
        image_BF_tip.setOnClickListener {
            requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LOCKED
            recyclerpopup.layoutManager= LinearLayoutManager(this)
            recyclerpopup.adapter=adapterPopUp(listofelemrntspopupBFtip,this)
            popuupWindow.isOutsideTouchable = true
            popuupWindow.isFocusable = true
            /* popuupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))*/
            popuupWindow.showAsDropDown(imageQuestion)
        }
        popupbackground.setOnClickListener {
            if (popuupWindow.isShowing)
            {
                popuupWindow.dismiss()
                requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }


        handler1=object : Handler(Looper.getMainLooper())
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
                        recyclerpopup.layoutManager=LinearLayoutManager(this@CalorieNeedActivity)
                        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this@CalorieNeedActivity)
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
                        recyclerpopup.layoutManager=LinearLayoutManager(this@CalorieNeedActivity)
                        recyclerpopup.adapter=adapterPopUp(listofelemrntspopup,this@CalorieNeedActivity)
                        popupbackground.forceLayout()

                    }
                }

            }
        }


        val drawable_sp_icons = ContextCompat.getDrawable(this,R.drawable.food_icon)?.apply {
            setBounds(0, 0, 400, 300)
        }
        textviewtp.setCompoundDrawables(null, drawable_sp_icons,null,null)

        val adapter_sport_normal = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return sorsman_normal_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=sorsman_normal_array[position]+ "  "
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=sorsman_normal_array[position]
                return view
            }
        }


        val adapter_gender = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return gender_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=weightUnit_array[position]
                return view
            }
        }

        val adapter_activity_amount = object : ArrayAdapter<Any>(
            this,
            R.layout.vievfordropdownlist
        )
        {
            override fun getCount(): Int {
                return activity_amount_array.size
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=activity_amount_array[position]+ "  "
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
                val view=this@CalorieNeedActivity.layoutInflater.inflate(R.layout.text_for_spinner,null)
                val textView=view.findViewById<TextView>(R.id.text_for_spinner)
                textView.text=activity_amount_array[position]
                return view
            }
        }
        spinner_weight_units.adapter=adapter_weight_unit
        spinner_height_units.adapter=adapter_height_unit
        spinner_gender.adapter=adapter_gender
        activity_amount.adapter=adapter_activity_amount
        spinner_sprtsman_normal.adapter=adapter_sport_normal

        spinner_sprtsman_normal.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                sportsman_normal=sorsman_normal_array[position]
                edit_tex_BF.isVisible = position==0
                image_BF_tip.isVisible = position==0
                edit_text_age.isVisible = position==1
                edit_text_height.isVisible = position==1
                spinner_height_units.isVisible= position==1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinner_gender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                gender_value=gender_array[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        var lastHeightUnit=0
        spinner_height_units.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    if (position==0)
                    {
                        height_uniit=0
                        if (lastHeightUnit==0)
                        {

                        }
                        else
                        {

                            edit_text_height.text= Editable.Factory.getInstance().newEditable(((((((edit_text_height.text.toString()).toDouble()*2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))

                        }
                        lastHeightUnit=0
                    }
                    else {
                        height_uniit=1
                        if (lastHeightUnit==0)
                        {

                            edit_text_height.text= Editable.Factory.getInstance().newEditable(((((((edit_text_height.text.toString().toDouble())/2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }
                        else
                        {

                        }
                        lastHeightUnit=1
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
        spinner_weight_units.onItemSelectedListener = object :
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

        activity_amount.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                chosen_activity_amount=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        check_box_base.setOnClickListener{

                check_box_base.isChecked=true
                check_box_activity.isChecked=false
            calculationOptionChosen=0
            activity_amount.isVisible=false

        }
        check_box_activity.setOnClickListener{

            check_box_base.isChecked=false
            check_box_activity.isChecked=true
            calculationOptionChosen=1
            activity_amount.isVisible=true

        }




        layout_Need.setOnClickListener {
            val focucedview=this.currentFocus
            focucedview?.clearFocus()
        }
        buttton_Calc.setOnClickListener {

            try {
                if (sportsman_normal==sorsman_normal_array[1]) {
                    if (height_uniit == 0) {
                        height1 = edit_text_height.text.toString().toDouble()
                    } else {
                        height1 = edit_text_height.text.toString().toDouble() * 2.54
                    }
                }

                if (weight_uniit==0)
                {
                    weight1=edit_text_weight.text.toString().toDouble()
                }
                else
                {
                    weight1=edit_text_weight.text.toString().toDouble()*0.453
                }
                if (sportsman_normal==sorsman_normal_array[1]) { age1=edit_text_age.text.toString().toDouble()}

                if (sportsman_normal==sorsman_normal_array[0])
                {
                    if (calculationOptionChosen == 0) {
                        calorieRequirementsValueforText=  500 + (22 * ((100.0-edit_tex_BF.text.toString().toDouble())/100.0)*weight1)
                    } else {
                        calorieRequirementsValueforText= ( 500 + (22 * ((100.0-edit_tex_BF.text.toString().toDouble())/100.0)*weight1))* activity_amount_coefs[chosen_activity_amount]

                    }
                }
                else {
                    if (calculationOptionChosen == 0) {
                        if (gender_value == gender_array[0]) {
                            calorieRequirementsValueforText =
                                66.0 + (13.7 * weight1) + (5 * height1) - (6.8 * age1)

                        } else {
                            calorieRequirementsValueforText =
                                655.0 + (9.6 * weight1) + (1.8 * height1) - (4.7 * age1)
                        }
                    } else {
                        if (gender_value == gender_array[0]) {
                            calorieRequirementsValueforText =
                                (66.0 + (13.7 * weight1) + (5 * height1) - (6.8 * age1)) * activity_amount_coefs[chosen_activity_amount]

                        } else {
                            calorieRequirementsValueforText =
                                (655.0 + (9.6 * weight1) + (1.8 * height1) - (4.7 * age1)) * activity_amount_coefs[chosen_activity_amount]
                        }

                    }
                }
                text_calorie_requirements.text=(((calorieRequirementsValueforText*10.0).toInt().toDouble())/10.0).toString()+" "+"kcal"
                text_calorie_requirements.startAnimation(animation)
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