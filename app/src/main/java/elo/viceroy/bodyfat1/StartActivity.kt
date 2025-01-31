package elo.viceroy.bodyfat1

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class StartActivity: AppCompatActivity() {


    var heightUnitArray=arrayOf<String>("cm","inch")
    var weighttUnitArray=arrayOf<String>("kg","lbs")
    var genderArray=arrayOf<String>("Male","Female")
    var weight:Double=0.0
    var height:Double=0.0
    var gender:String?=genderArray[1]
    var weightUnit:String=weighttUnitArray[0]
    var heightUnit:String=heightUnitArray[0]

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startactivity)

        val spinnerWeightUnits=findViewById<Spinner>(R.id.weight_unit)
        val spinnerHeightUnits=findViewById<Spinner>(R.id.height_unit)
        val spinnerGender=findViewById<Spinner>(R.id.gender_list)
        val heightEditText=findViewById<EditText>(R.id.height)
        val weightEditText=findViewById<EditText>(R.id.weight)
        val startactivtylayout=findViewById<ConstraintLayout>(R.id.startactivtylayout)
        val buttonOK=findViewById<Button>(R.id.OK)


        val adapter_height_unit=object: ArrayAdapter<Any>(this,
            R.layout.vievfordropdownlist)
        {


            override fun getCount(): Int {
                return heightUnitArray.size
            }


            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val viewrt=this@StartActivity.layoutInflater.inflate(R.layout.viueworarraydapter,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                val arrowdown=viewrt.findViewById<ImageView>(R.id.arrowforspinner)
                val arrowicon= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.arrowderty),60,60)
                arrowdown.setImageBitmap(arrowicon)
                textr.text=heightUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.heigtcm),50,50)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.heightinch),50,50)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {


                val viewrt=this@StartActivity.layoutInflater.inflate(R.layout.vievfordropdownlist,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                textr.text=heightUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.heigtcm),50,50)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.heightinch),50,50)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


        }


        val adapter_weight_unit=object: ArrayAdapter<Any>(this,
            R.layout.vievfordropdownlist)
        {


            override fun getCount(): Int {
                return weighttUnitArray.size
            }


            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val viewrt=this@StartActivity.layoutInflater.inflate(R.layout.viueworarraydapter,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                val arrowdown=viewrt.findViewById<ImageView>(R.id.arrowforspinner)
                val arrowicon= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.arrowderty),60,60)
                arrowdown.setImageBitmap(arrowicon)
                textr.text=weighttUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.kgwei),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.lbswei),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {


                val viewrt=this@StartActivity.layoutInflater.inflate(R.layout.vievfordropdownlist,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                textr.text=weighttUnitArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.kgwei),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.lbswei),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


        }

        val adapter_wgender=object: ArrayAdapter<Any>(this,
            R.layout.vievfordropdownlist)
        {


            override fun getCount(): Int {
                return genderArray.size
            }


            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val viewrt=this@StartActivity.layoutInflater.inflate(R.layout.viueworarraydapter,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                val arrowdown=viewrt.findViewById<ImageView>(R.id.arrowforspinner)
                val arrowicon= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.arrowderty),60,60)
                arrowdown.setImageBitmap(arrowicon)
                textr.text=genderArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.male),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.female),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {


                val viewrt=this@StartActivity.layoutInflater.inflate(R.layout.vievfordropdownlist,null)
                val textr=viewrt.findViewById<TextView>(R.id.tekstunitgender)
                val imageicon=viewrt.findViewById<ImageView>(R.id.photoicon)
                textr.text=genderArray[position]+" "
                if (position==0) {
                    val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.male),60,60)
                    imageicon.setImageBitmap(emojibitmap)
                }
                else
                {val emojibitmap= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(this@StartActivity.resources,R.drawable.female),60,60)
                    imageicon.setImageBitmap(emojibitmap)

                }
                return viewrt
            }


        }



        spinnerHeightUnits.adapter=adapter_height_unit
        spinnerWeightUnits.adapter=adapter_weight_unit
        spinnerGender.adapter=adapter_wgender

        var lastHeightUnit=0
        var lastWeightUnit=0


        spinnerHeightUnits.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {


                try{
                    val actualHeight=heightEditText.text.toString().toDouble()
                    if (position==0)
                    {
                        if (lastHeightUnit==0)
                        {
                        }
                        else
                        {
                            heightEditText.text = Editable.Factory.getInstance().newEditable((((((actualHeight*2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                    else if(position==1)
                    {
                        if (lastHeightUnit==1)
                        {
                        }
                        else
                        {
                            heightEditText.text = Editable.Factory.getInstance().newEditable((((((actualHeight/2.54)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }

                }
                catch (e:Exception)
                {
                }
                heightUnit=heightUnitArray[position]
                lastHeightUnit=position

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerWeightUnits.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                try{
                    val actualWeight=weightEditText.text.toString().toDouble()
                    if (position==0)
                    {
                        if (lastWeightUnit==0)
                        {
                        }
                        else
                        {
                            weightEditText.text = Editable.Factory.getInstance().newEditable((((((actualWeight*0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
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
                            weightEditText.text = Editable.Factory.getInstance().newEditable((((((actualWeight/0.453)*10).toInt().toDouble())/10.0).roundToInt()).toString().format(
                                Locale.US, "%.2f"))
                        }

                    }
                }
                catch (e:Exception)
                {

                }
                lastWeightUnit=position
                weightUnit=weighttUnitArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinnerGender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                gender=genderArray[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        startactivtylayout.setOnClickListener {
            val focucedview=this.currentFocus
            focucedview?.clearFocus()
        }

buttonOK.setOnClickListener {
    try {

        height=heightEditText.text.toString().toDouble()
        val intent1 = Intent(this@StartActivity, InstructionActivity::class.java)
        if (weightUnit==weighttUnitArray[0])
        {
            weight=weightEditText.text.toString().toDouble()
        }
        else
        {
            weight=weightEditText.text.toString().toDouble()*0.453
        }

        if (heightUnit==heightUnitArray[0])
        {
            height=heightEditText.text.toString().toDouble()
        }
        else
        {
            height=heightEditText.text.toString().toDouble()*2.54
        }

        intent1.putExtra("factor", (weight/(height/100).pow(2)).toString()).putExtra("gender",gender)
        startActivity(intent1)
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
}